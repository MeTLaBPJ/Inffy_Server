package org.inffy.domain.chatroom.service;

import lombok.RequiredArgsConstructor;
import org.inffy.domain.chatroom.dto.req.ChatroomCreateRequestDto;
import org.inffy.domain.chatroom.dto.req.ChatroomScheduleRequestDto;
import org.inffy.domain.chatroom.dto.res.*;
import org.inffy.domain.chatroom.entity.ChatJoin;
import org.inffy.domain.chatroom.entity.Chatroom;
import org.inffy.domain.chatroom.enums.RoomType;
import org.inffy.domain.chatroom.repository.ChatJoinRepository;
import org.inffy.domain.chatroom.repository.ChatroomRepository;
import org.inffy.domain.member.dto.res.MemberSummaryResponseDto;
import org.inffy.domain.member.entity.Member;
import org.inffy.domain.member.enums.Gender;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final ChatJoinRepository chatJoinRepository;

    @Transactional
    public Long createChatroom(Member member, ChatroomCreateRequestDto req){
        Chatroom chatroom = Chatroom.builder()
                .host(member)
                .title(req.getTitle())
                .subtitle(req.getSubtitle())
                .maxMaleMembers(req.getMaxMaleMembers())
                .maxFemaleMembers(req.getMaxFemaleMembers())
                .build();

        chatroomRepository.save(chatroom);

        return chatroom.getId();
    }

    @Transactional(readOnly = true)
    public List<ChatroomParticipationResponseDto> findParticipatingChatroom(Member member){
        return chatroomRepository.findChatroomByMemberId(member.getId()).stream()
                .map(chatroom -> ChatroomParticipationResponseDto.builder()
                        .roomId(chatroom.getId())
                        .title(chatroom.getTitle())
                        .subtitle(chatroom.getSubtitle())
                        .maxMaleMembers(chatroom.getMaxMaleMembers())
                        .maxFemaleMembers(chatroom.getMaxFemaleMembers())
                        .isHost(chatroom.getHost().getId().equals(member.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatroomRecruitingResponseDto> findRecruitingChatroom(){
        return chatroomRepository.findByIsStartedFalseOrderByUpdatedAtDesc().stream()
                .map(chatroom -> ChatroomRecruitingResponseDto.builder()
                        .roomId(chatroom.getId())
                        .title(chatroom.getTitle())
                        .subtitle(chatroom.getSubtitle())
                        .maxMaleMembers(chatroom.getMaxMaleMembers())
                        .maxFemaleMembers(chatroom.getMaxFemaleMembers())
                        .currentMaleCount(chatroom.getCurrentMaleCount())
                        .currenFemaleCount(chatroom.getCurrentFemaleCount())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatroomDetailResponseDto findChatroomDetail(Member member, Long roomId){
        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        ChatJoin chatJoin = chatJoinRepository.findByMemberIdAndChatroomId(member.getId(), chatroom.getId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_IN_CHATROOM));

        List<MemberSummaryResponseDto> allMembers = chatroomRepository.findAllMemberSummaryByChatroomId(roomId);

        List<MemberSummaryResponseDto> maleMembers = allMembers.stream()
                .filter(qMember -> qMember.getGender() == Gender.MALE)
                .toList();

        List<MemberSummaryResponseDto> femaleMembers = allMembers.stream()
                .filter(qMember -> qMember.getGender() == Gender.FEMALE)
                .toList();

        return ChatroomDetailResponseDto.builder()
                .title(chatroom.getTitle())
                .deadLine(chatroom.getDeadLine())
                .maleMemberSummary(maleMembers)
                .femaleMemberSummary(femaleMembers)
                .build();
    }

    @Transactional(readOnly = true)
    public ChatroomParticipantResponseDto getChatroomParticipants(Long roomId){
        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        List<MemberSummaryResponseDto> allMembers = chatroomRepository.findAllMemberSummaryByChatroomId(roomId);

        List<MemberSummaryResponseDto> maleMembers = allMembers.stream()
                .filter(qMember -> qMember.getGender() == Gender.MALE)
                .toList();

        List<MemberSummaryResponseDto> femaleMembers = allMembers.stream()
                .filter(qMember -> qMember.getGender() == Gender.FEMALE)
                .toList();

        return ChatroomParticipantResponseDto.builder()
                .maleMemberSummary(maleMembers)
                .femaleMemberSummary(femaleMembers)
                .build();
    }

    @Transactional
    public Boolean participateChatroom(Member member, Long roomId){

        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        validateChatroomParticipate(chatroom, member);

        ChatJoin chatJoin = ChatJoin.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        chatJoinRepository.save(chatJoin);

        chatroom.addChatJoin(chatJoin, member.getGender());
        member.addChatJoin(chatJoin);

        return true;
    }

    @Transactional
    public ChatroomStartResponseDto startChatroom(Member member, Long roomId){

        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        isHost(member, chatroom);
        checkChatroomStart(chatroom);

        chatroom.start();

        return ChatroomStartResponseDto.builder()
                .roomId(chatroom.getId())
                .title(chatroom.getTitle())
                .hostNickname(member.getNickname())
                .build();
    }

    @Transactional
    public Boolean scheduleChatroom(Member member, Long roomId, ChatroomScheduleRequestDto req){
        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        isHost(member, chatroom);
        chatroom.updateDeadLine(req.getDeadLine());

        return true;
    }

    @Transactional
    public Long leaveChatroom(Member member, Long roomId){
        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        ChatJoin chatJoin = chatJoinRepository.findByMemberIdAndChatroomId(member.getId(), chatroom.getId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_IN_CHATROOM));

        chatJoinRepository.delete(chatJoin);

        chatroom.decrementGenderCount(member.getGender());

        return member.getId();
    }

    @Transactional
    public Long deleteChatroom(Member member, Long roomId){
        Chatroom chatroom = chatroomRepository.findById(roomId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CHATROOM_NOT_FOUND));

        isHost(member, chatroom);

        chatroomRepository.delete(chatroom);

        return roomId;
    }

    private void isHost(Member member, Chatroom chatroom){
        if(!chatroom.isHost(member))
            throw new RestApiException(CustomErrorCode.CHATROOM_PERMISSION_DENIED);
    }

    private void checkChatroomStart(Chatroom chatroom){
        if(chatroom.getRoomType() == RoomType.MEETING){
            if(!Objects.equals(chatroom.getCurrentMaleCount(), chatroom.getMaxMaleMembers())
                    || !Objects.equals(chatroom.getCurrentFemaleCount(), chatroom.getMaxFemaleMembers()))
                throw new RestApiException(CustomErrorCode.CHATROOM_NOT_FULL);
        }else{
            if(!Objects.equals(chatroom.getCurrentMemberCount(), chatroom.getMaxMembers()))
                throw new RestApiException(CustomErrorCode.CHATROOM_NOT_FULL);
        }
    }

    private void validateChatroomParticipate(Chatroom chatroom, Member member){
        validateMemberTicket(member);
        validateDuplicateParticipation(chatroom, member);
        validateChatroomStatus(chatroom);
        validateCapacity(chatroom, member);
    }

    private void validateMemberTicket(Member member) {
        if(member.getTicket() < 0)
            throw new RestApiException(CustomErrorCode.MEMBER_TICKET_NOT_ENOUGH);
    }

    private void validateDuplicateParticipation(Chatroom chatroom, Member member) {
        if(chatJoinRepository.existsByMemberIdAndChatroomId(member.getId(), chatroom.getId()))
            throw new RestApiException(CustomErrorCode.MEMBER_ALREADY_PARTICIPATED);
    }

    private void validateChatroomStatus(Chatroom chatroom) {
        if(chatroom.getIsStarted() || chatroom.getDeadLine().isBefore(LocalDate.now(ZoneId.of("Asia/Seoul"))))
            throw new RestApiException(CustomErrorCode.CHATROOM_NOT_AVAILABLE);
    }

    private void validateCapacity(Chatroom chatroom, Member member) {
        if(chatroom.getRoomType() == RoomType.MEETING) {
            validateMeetingRoomCapacity(chatroom, member);
            return;
        }
        validateCasualRoomCapacity(chatroom);
    }

    private void validateMeetingRoomCapacity(Chatroom chatroom, Member member) {
        boolean isFull = member.getGender() == Gender.MALE
                ? chatroom.getCurrentMaleCount() + 1 > chatroom.getMaxMaleMembers()
                : chatroom.getCurrentFemaleCount() + 1 > chatroom.getMaxFemaleMembers();

        if(isFull)
            throw new RestApiException(CustomErrorCode.CHATROOM_IS_FULL);
    }

    private void validateCasualRoomCapacity(Chatroom chatroom) {
        if(chatroom.getCurrentMemberCount() + 1 > chatroom.getMaxMembers())
            throw new RestApiException(CustomErrorCode.CHATROOM_IS_FULL);
    }
}
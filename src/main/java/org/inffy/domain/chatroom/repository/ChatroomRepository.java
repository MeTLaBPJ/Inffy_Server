package org.inffy.domain.chatroom.repository;

import org.inffy.domain.chatroom.entity.Chatroom;
import org.inffy.domain.member.dto.res.MemberSummaryResponseDto;
import org.inffy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    @Query(value = "SELECT c FROM Chatroom c " +
            "JOIN c.chatJoins cj " +
            "WHERE cj.member.id = :memberId " +
            "ORDER BY c.updatedAt DESC")
    List<Chatroom> findChatroomByMemberId(@Param("memberId") Long memberId);

    List<Chatroom> findByIsStartedFalseOrderByUpdatedAtDesc();

    @Query("SELECT new org.inffy.domain.member.dto.res.MemberSummaryResponseDto(" +
            "m.department, m.username, m.nickname, m.gender) " +
            "FROM ChatJoin cj " +
            "JOIN cj.member m " +
            "WHERE cj.chatroom.id = :chatroomId")
    List<MemberSummaryResponseDto> findAllMemberSummaryByChatroomId(Long chatroomId);


    @Query(value = "SELECT m.fcmToken " +
            "FROM Chatroom cr " +
            "JOIN cr.chatJoins cj " +
            "JOIN cj.member m " +
            "WHERE cr.id = :chatroomId " +
            "AND cj.active = false")
    List<String> findFcmTokensByChatroomId(@Param("chatroomId") Long chatroomId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Chatroom c WHERE c.deadLine < :deadLine")
    void deleteAllByDeadLineBefore(@Param("deadLine") LocalDate deadLine);
}
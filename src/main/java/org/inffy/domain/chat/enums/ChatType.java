package org.inffy.domain.chat.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ChatType {

    ENTER("ENTER", "입장메시지"),
    CHAT("CHAT", "일반메시지"),
    LEAVE("LEAVE", "퇴장메시지");


    private final String type;

    @JsonValue
    private final String name;


    @JsonCreator
    public static ChatType fromChatType(String value){
        return Arrays.stream(values())
                .filter(chatType -> chatType.getType().equals(value))
                .findAny()
                .orElse(null);
    }
}

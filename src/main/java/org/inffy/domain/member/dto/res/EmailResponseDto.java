package org.inffy.domain.member.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResponseDto {
    private boolean success;
    private String schoolEmail;
    private String authNumber;
}
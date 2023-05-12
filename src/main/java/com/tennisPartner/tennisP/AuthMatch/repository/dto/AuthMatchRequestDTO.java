package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthMatchRequestDTO {
    int gameCnt;
    int courtCnt;
    String matchType;
}

package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthMatchRequestDTO {
    int gameCnt;
    int courtCnt;
    String matchType;
}

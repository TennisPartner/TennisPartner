package com.tennisPartner.tennisP.user.repository.dto;

import com.tennisPartner.tennisP.user.domain.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReCreateTokenResponseDto {

    private RefreshToken refreshToken;
    private String accessToken;

}

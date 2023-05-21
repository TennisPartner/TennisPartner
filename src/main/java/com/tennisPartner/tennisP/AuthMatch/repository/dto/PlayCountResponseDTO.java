package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayCountResponseDTO {
    GetUserResponseDto user;
    int playCnt;

    public PlayCountResponseDTO(GetUserResponseDto user, int playCnt){
        this.user = user;
        this.playCnt = playCnt;
    }
}

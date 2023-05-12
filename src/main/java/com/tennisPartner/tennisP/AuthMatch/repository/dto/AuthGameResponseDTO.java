package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import com.tennisPartner.tennisP.AuthMatch.domain.AuthGame;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthGameResponseDTO {
    Long authGameIdx;
    Long authMatchIdx;
    GetUserResponseDto host1;
    GetUserResponseDto host2;
    GetUserResponseDto guest1;
    GetUserResponseDto guest2;
    int courtSequence;
    int hostScore;
    int guestScore;

    public AuthGameResponseDTO(AuthGame Entity){
        this.authGameIdx = Entity.getAuthGameIdx();
        this.authMatchIdx = Entity.getAuthMatch().getAuthMatchIdx();
        this.host1 = new GetUserResponseDto(Entity.getHost1());
        this.host2 = new GetUserResponseDto(Entity.getHost2());
        this.guest1 = new GetUserResponseDto(Entity.getGuest1());
        this.guest2 = new GetUserResponseDto(Entity.getGuest2());
        this.courtSequence = Entity.getCourtSequence();
        this.hostScore = Entity.getHostScore();
        this.guestScore = Entity.getGuestScore();
    }
}

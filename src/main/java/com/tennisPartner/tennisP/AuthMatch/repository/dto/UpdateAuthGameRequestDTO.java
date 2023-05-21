package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAuthGameRequestDTO {
    Long host1Idx;
    Long host2Idx;
    Long guest1Idx;
    Long guest2Idx;
    int hostScore;
    int guestScore;

    @Builder
    public UpdateAuthGameRequestDTO(Long host1Idx, Long host2Idx, Long guest1Idx, Long guest2Idx, int hostScore, int guestScore){
        this.host1Idx = host1Idx;
        this.host2Idx = host2Idx;
        this.guest1Idx = guest1Idx;
        this.guest2Idx = guest2Idx;
        this.hostScore = hostScore;
        this.guestScore = guestScore;
    }

}

package com.tennisPartner.tennisP.club.repository.dto;

import com.tennisPartner.tennisP.club.domain.Club;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubResponseDTO {
    private Long clubIdx;
    private String clubName;
    private String clubInfo;
    private String clubCity;
    private String clubCounty;
    private char useYn;

    @Builder
    public ClubResponseDTO(Club Entity){
        this.clubIdx = Entity.getClubIdx();
        this.clubName = Entity.getClubName();
        this.clubInfo = Entity.getClubInfo();
        this.clubCity = Entity.getClubCity();
        this.clubCounty = Entity.getClubCounty();
        this.useYn = Entity.getUseYn();
    }
}
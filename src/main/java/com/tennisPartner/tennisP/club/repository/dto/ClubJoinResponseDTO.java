package com.tennisPartner.tennisP.club.repository.dto;

import com.tennisPartner.tennisP.club.domain.ClubJoin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubJoinResponseDTO {
    private Long clubJoinIdx;
    private ClubResponseDTO clubDTO;

    private Long userIdx;
    private String clubGrade;
    private char useYn;

    @Builder
    public ClubJoinResponseDTO(ClubJoin Entity) {
        this.clubJoinIdx = Entity.getClubJoinIdx();
        this.clubDTO = new ClubResponseDTO(Entity.getClub());
        this.userIdx =  Entity.getUser().getUserIdx();
        this.clubGrade =  Entity.getClubGrade();
        this.useYn =  Entity.getUseYn();
    }
}

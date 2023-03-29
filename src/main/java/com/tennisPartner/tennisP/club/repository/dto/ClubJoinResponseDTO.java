package com.tennisPartner.tennisP.club.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubJoinResponseDTO {
    private Long clubJoinIdx;
    private Club club;

    private Long userIdx;
    private String clubGrade;
    private char useYn;

    @Builder
    public ClubJoinResponseDTO(ClubJoin Entity) {
        this.clubJoinIdx = Entity.getClubJoinIdx();
        this.club = Entity.getClub();
        this.userIdx =  Entity.getUser().getUserIdx();
        this.clubGrade =  Entity.getClubGrade();
        this.useYn =  Entity.getUseYn();
    }
}

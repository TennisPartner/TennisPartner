package com.tennisPartner.tennisP.club.repository.dto;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.user.entity.User;
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
    private User user;
    private String clubGrade;
    private char useYn;

    @Builder
    public ClubJoinResponseDTO(ClubJoin Entity) {
        this.clubJoinIdx = Entity.getClubJoinIdx();
        this.club = Entity.getClub();
        this.user =  Entity.getUser();
        this.clubGrade =  Entity.getClubGrade();
        this.useYn =  Entity.getUseYn();
    }
}

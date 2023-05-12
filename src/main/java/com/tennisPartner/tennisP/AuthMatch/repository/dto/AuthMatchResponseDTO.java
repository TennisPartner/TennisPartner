package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import com.tennisPartner.tennisP.AuthMatch.domain.AuthMatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMatchResponseDTO {

    Long AuthMatchIdx;
    Long clubBoardIdx;
    Long ownerIdx;
    String matchType;
    int userCnt;
    int gameCnt;
    int courtCnt;

    public AuthMatchResponseDTO(AuthMatch Entity){
        this.AuthMatchIdx = Entity.getAuthMatchIdx();
        this.clubBoardIdx = Entity.getClubBoard().getClubBoardIdx();
        this.ownerIdx = Entity.getOwner().getUserIdx();
        this.matchType = Entity.getMatchType();
        this.userCnt = Entity.getUserCnt();
        this.gameCnt = Entity.getGameCnt();
        this.courtCnt = Entity.getCourtCnt();
    }
}

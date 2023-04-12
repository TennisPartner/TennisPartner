package com.tennisPartner.tennisP.clubBoard.repository.dto;


import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubBoardJoinResponseDTO {

    private Long ClubBoardJoinIdx;
    private ClubBoardResponseDTO clubBoardDTO;
    private Long userIdx;

    public ClubBoardJoinResponseDTO(ClubBoardJoin Entity){
        this.ClubBoardJoinIdx = Entity.getClubBoardJoinIdx();
        this.clubBoardDTO = new ClubBoardResponseDTO(Entity.getClubBoard());
        this.userIdx = Entity.getUser().getUserIdx();
    }
}

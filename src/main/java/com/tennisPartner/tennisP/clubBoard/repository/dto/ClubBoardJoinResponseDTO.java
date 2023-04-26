package com.tennisPartner.tennisP.clubBoard.repository.dto;


import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubBoardJoinResponseDTO {

    private Long ClubBoardJoinIdx;
    private GetUserResponseDto userDTO;

    public ClubBoardJoinResponseDTO(ClubBoardJoin Entity){
        this.ClubBoardJoinIdx = Entity.getClubBoardJoinIdx();
        this.userDTO= new GetUserResponseDto(Entity.getUser());
    }
}

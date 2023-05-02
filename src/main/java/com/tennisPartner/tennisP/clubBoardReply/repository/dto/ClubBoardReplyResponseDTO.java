package com.tennisPartner.tennisP.clubBoardReply.repository.dto;


import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.clubBoardReply.domain.ClubBoardReply;

import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubBoardReplyResponseDTO {

    private Long clubBoardReplyIdx;
    private Long clubBoardIdx;
    private GetUserResponseDto writer;
    private String replyContents;

    public ClubBoardReplyResponseDTO(ClubBoardReply Entity){
        this.clubBoardReplyIdx = Entity.getClubBoardReplyIdx();
        this.clubBoardIdx = Entity.getClubBoard().getClubBoardIdx();
        this.writer = new GetUserResponseDto(Entity.getWriter());
        this.replyContents = Entity.getReplyContents();
    }

}

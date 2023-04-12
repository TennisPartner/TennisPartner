package com.tennisPartner.tennisP.clubBoard.repository.dto;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.user.domain.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubBoardResponseDTO {
    private Long clubBoardIdx;
    private ClubResponseDTO clubDTO;
    private Long writer;
    private char clubBoardType;
    private String clubBoardTitle;
    private String clubBoardContents;
    private int wantedCnt;
    private LocalDateTime meetDt;
    private char useYn;


    public ClubBoardResponseDTO(ClubBoard Entity){
        this.clubBoardIdx = Entity.getClubBoardIdx();
        this.clubDTO = new ClubResponseDTO(Entity.getClub());
        this.writer = Entity.getWriter().getUserIdx();
        this.clubBoardType = Entity.getClubBoardType();
        this.clubBoardTitle = Entity.getClubBoardTitle();
        this.clubBoardContents = Entity.getClubBoardContents();
        this.wantedCnt = Entity.getWantedCnt();
        this.meetDt = Entity.getMeetDt();
        this.useYn = Entity.getUseYn();
    }
}

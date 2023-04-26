package com.tennisPartner.tennisP.clubBoard.repository.dto;

import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubBoardResponseDTO {
    private Long clubBoardIdx;
    private ClubResponseDTO clubDTO;
    private GetUserResponseDto writerDTO;
    private String clubBoardType;
    private String clubBoardTitle;
    private String clubBoardContents;
    private int wantedCnt;
    private LocalDateTime meetDt;

    private List<ClubBoardJoinResponseDTO> joinList = new ArrayList<>();
    private String useYn;


    public ClubBoardResponseDTO(ClubBoard Entity){
        this.clubBoardIdx = Entity.getClubBoardIdx();
        this.clubDTO = new ClubResponseDTO(Entity.getClub());
        this.writerDTO = new GetUserResponseDto(Entity.getWriter());
        this.clubBoardType = Entity.getClubBoardType();
        this.clubBoardTitle = Entity.getClubBoardTitle();
        this.clubBoardContents = Entity.getClubBoardContents();
        this.wantedCnt = Entity.getWantedCnt();
        this.meetDt = Entity.getMeetDt();
        if(Entity.getBoardJoinList() != null){
            this.joinList = Entity.getBoardJoinList().stream().map(j -> new ClubBoardJoinResponseDTO(j)).collect(
                Collectors.toList());
        }

        this.useYn = Entity.getUseYn();
    }
}

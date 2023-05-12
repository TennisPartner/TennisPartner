package com.tennisPartner.tennisP.clubBoard.repository.dto;


import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchResponseDTO;
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
    private Long clubIdx;
    private GetUserResponseDto writerDTO;
    private String clubBoardType;
    private String clubBoardTitle;
    private String clubBoardContents;
    private int wantedCnt;
    private LocalDateTime meetDt;

    private List<ClubBoardJoinResponseDTO> joinList = new ArrayList<>();
    private AuthMatchResponseDTO authMatchDTO;
    private String useYn;


    public ClubBoardResponseDTO(ClubBoard Entity){
        this.clubBoardIdx = Entity.getClubBoardIdx();
        this.clubIdx = Entity.getClub().getClubIdx();
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
        if(Entity.getAuthMatch() != null)
        this.authMatchDTO = new AuthMatchResponseDTO(Entity.getAuthMatch());
        this.useYn = Entity.getUseYn();
    }
}

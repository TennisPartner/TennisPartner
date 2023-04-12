package com.tennisPartner.tennisP.clubBoard.repository.dto;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.user.domain.User;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ClubBoardRequestDTO {

    private Long clubBoardIdx;
    private char clubBoardType;
    @NotBlank(message = "게시물의 제목을 입력해 주시기 바랍니다")
    private String clubBoardTitle;
    @NotBlank(message = "게시물의 내용을 작성해 주시기 바랍니다.")
    private String clubBoardContents;
    private int wantedCnt;
    private LocalDateTime meetDt;
    private char useYn;

    public ClubBoard dtoToClubBoardEntity(Club club, User writer){
        return ClubBoard.builder()
            .clubBoardIdx(clubBoardIdx)
            .clubBoardTitle(clubBoardTitle)
            .clubBoardContents(clubBoardContents)
            .clubBoardType(clubBoardType)
            .club(club)
            .writer(writer)
            .wantedCnt(wantedCnt)
            .meetDt(meetDt)
            .useYn(useYn)
            .build();
    }


}

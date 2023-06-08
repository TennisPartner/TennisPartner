package com.tennisPartner.tennisP.board.repository.dto;

import com.tennisPartner.tennisP.board.domain.Board;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateBoardRequestDto {

    private String boardTitle;
    private String boardContents;
    private Long uplBoardIdx;
    private String uplPath;
    private String useYn;

}

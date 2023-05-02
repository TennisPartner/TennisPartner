package com.tennisPartner.tennisP.board.repository.dto;

import com.tennisPartner.tennisP.board.domain.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateBoardRequestDto {

    private String boardTitle;
    private String boardContents;
    private String useYn;

}

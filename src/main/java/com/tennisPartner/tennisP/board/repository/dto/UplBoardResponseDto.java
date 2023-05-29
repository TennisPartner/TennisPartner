package com.tennisPartner.tennisP.board.repository.dto;

import com.tennisPartner.tennisP.board.domain.UplBoard;
import lombok.Getter;

@Getter
public class UplBoardResponseDto {

    private Long uplBoardIdx;
    private String uplPath;

    public UplBoardResponseDto(UplBoard entity) {
        this.uplBoardIdx = entity.getUplBoardIdx();
        this.uplPath = entity.getUplPath();
    }

}

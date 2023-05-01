package com.tennisPartner.tennisP.board.repository.dto;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GetBoardResponseDto {

    private Long boardIdx;
    private User writer;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime updateDt;

    public GetBoardResponseDto(Board entity) {
        this.boardIdx = entity.getBoardIdx();
        this.writer = entity.getWriter();
        this.boardTitle = entity.getBoardTitle();
        this.boardContents = entity.getBoardContents();
        this.updateDt = entity.getUpdateDt();
    }
}

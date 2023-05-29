package com.tennisPartner.tennisP.board.repository.dto;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.domain.UplBoard;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class GetBoardResponseDto {

    private Long boardIdx;
    private GetUserResponseDto writer;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime updateDt;

    private List<UplBoardResponseDto> uplBoards;

    public GetBoardResponseDto(Board entity) {
        this.boardIdx = entity.getBoardIdx();
        this.writer = new GetUserResponseDto(entity.getWriter());
        this.boardTitle = entity.getBoardTitle();
        this.boardContents = entity.getBoardContents();
        this.updateDt = entity.getUpdateDt();
        this.uplBoards = entity.getUplBoards().stream()
                .map(u -> new UplBoardResponseDto(u))
                .collect(Collectors.toList());
    }
}

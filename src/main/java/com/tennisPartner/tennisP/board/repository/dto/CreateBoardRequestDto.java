package com.tennisPartner.tennisP.board.repository.dto;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateBoardRequestDto {

    @NotBlank(message = "게시물 제목을 작성해주세요.")
    private String boardTitle;
    @NotBlank(message = "게시물 내용을 작성해주세요.")
    private String boardContents;

    public Board DtoToBoardEntity(User writer) {
        return Board.builder()
                .writer(writer)
                .boardTitle(this.boardTitle)
                .boardContents(this.boardContents)
                .build();
    }

}

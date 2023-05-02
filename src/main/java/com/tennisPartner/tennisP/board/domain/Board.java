package com.tennisPartner.tennisP.board.domain;

import com.tennisPartner.tennisP.board.repository.dto.UpdateBoardRequestDto;
import com.tennisPartner.tennisP.common.domain.BaseTimeEntity;
import com.tennisPartner.tennisP.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="board_tb")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long boardIdx;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="writer_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User writer;
    @Column(nullable = false)
    private String boardTitle;
    @Column(nullable = false)
    private String boardContents;
    private String useYn;

    @PrePersist
    public void persistBoard() {
        this.useYn = "Y";
    }

    @Builder
    public Board(User writer, String boardTitle, String boardContents) {
        this.writer = writer;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
    }

    public void updateBoard(UpdateBoardRequestDto updateBoardRequestDto) {
        this.boardTitle = updateBoardRequestDto.getBoardTitle();
        this.boardContents = updateBoardRequestDto.getBoardContents();
        this.useYn = updateBoardRequestDto.getUseYn();
    }

}

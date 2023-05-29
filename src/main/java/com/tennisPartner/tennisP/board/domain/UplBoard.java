package com.tennisPartner.tennisP.board.domain;

import com.tennisPartner.tennisP.common.domain.CreateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "upl_board_tb")
public class UplBoard extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uplBoardIdx;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="board_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;
    private String uplPath;


    @Builder
    public UplBoard(Board board, String uplPath) {
        this.board = board;
        this.uplPath = uplPath;
    }

}

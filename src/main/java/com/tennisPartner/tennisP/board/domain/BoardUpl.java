package com.tennisPartner.tennisP.board.domain;

import com.tennisPartner.tennisP.common.domain.CreateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "board_upl_tb")
public class BoardUpl extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardUplIdx;
    private Long boardIdx;
    private String uplPath;


    @Builder
    public BoardUpl(Long boardIdx, String uplPath) {
        this.boardIdx = boardIdx;
        this.uplPath = uplPath;
    }

}

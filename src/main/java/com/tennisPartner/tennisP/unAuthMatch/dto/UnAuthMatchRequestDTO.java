package com.tennisPartner.tennisP.unAuthMatch.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UnAuthMatchRequestDTO {
    private int playerCnt;
    private int gameCnt;
    private int courtCnt;

    public UnAuthMatchRequestDTO(int playerCnt, int gameCnt, int courtCnt){
        this.playerCnt = playerCnt;
        this.gameCnt = gameCnt;
        this.courtCnt = courtCnt;
    }

}

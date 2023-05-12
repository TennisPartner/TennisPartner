package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchResultResponseDTO implements Comparable<MatchResultResponseDTO>{
    GetUserResponseDto user;
    int winCnt;
    int loseCnt;
    int score;
    @Builder
    public MatchResultResponseDTO(GetUserResponseDto user, int winCnt, int loseCnt, int score){
        this.user = user;
        this.winCnt = winCnt;
        this.loseCnt = loseCnt;
        this.score = score;
    }

    @Override
    public int compareTo(MatchResultResponseDTO o) {
        if(o.winCnt == this.winCnt) return o.score - this.score;
        return o.winCnt - this.winCnt;
    }
}

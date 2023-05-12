package com.tennisPartner.tennisP.AuthMatch.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAuthGameRequestDTO {
    Long host1_idx;
    Long host2_idx;
    Long guest1_idx;
    Long guest2_idx;
    int host_score;
    int guest_score;

    public UpdateAuthGameRequestDTO(Long host1_idx, Long host2_idx, Long guest1_idx, Long guest2_idx, int host_score, int guest_score){
        this.host1_idx = host1_idx;
        this.host2_idx = host2_idx;
        this.guest1_idx = guest1_idx;
        this.guest2_idx = guest2_idx;
        this.host_score = host_score;
        this.guest_score = guest_score;
    }

}

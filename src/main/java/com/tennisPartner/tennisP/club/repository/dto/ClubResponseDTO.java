package com.tennisPartner.tennisP.club.repository.dto;

import com.tennisPartner.tennisP.club.domain.Club;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubResponseDTO {
    private Long clubIdx;
    private String clubName;
    private String clubInfo;
    private String clubCity;
    private String clubCounty;
    private List<ClubJoinResponseDTO> joinList = new ArrayList<>();
    private String useYn;

    @Builder
    public ClubResponseDTO(Club Entity){
        this.clubIdx = Entity.getClubIdx();
        this.clubName = Entity.getClubName();
        this.clubInfo = Entity.getClubInfo();
        this.clubCity = Entity.getClubCity();
        this.clubCounty = Entity.getClubCounty();
        this.joinList = Entity.getJoinList().stream().filter(h -> h.getUseYn().equals("Y"))
            .map(j -> new ClubJoinResponseDTO(j)).collect(Collectors.toList());
        this.useYn = Entity.getUseYn();
    }
}


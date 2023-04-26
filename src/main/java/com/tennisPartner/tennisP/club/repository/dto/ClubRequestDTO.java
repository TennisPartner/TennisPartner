package com.tennisPartner.tennisP.club.repository.dto;

import com.tennisPartner.tennisP.club.domain.Club;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubRequestDTO {
    private Long clubIdx;

    @NotBlank(message = "클럽명을 입력하여 주시기 바랍니다")
    @Size(min=2, max=20, message = "클럽명은 2~20 자리를 맞춰주시기 바랍니다.")
    private String clubName;
    private String clubInfo;
    @NotBlank(message = "지역을 선택해 주시기 바랍니다")
    private String clubCity;
    @NotBlank(message = "지역을 선택해 주시기 바랍니다")
    private String clubCounty;
    private String useYn;

    @Builder
    public ClubRequestDTO(Long clubIdx, String clubName, String clubInfo, String clubCity, String clubCounty, String useYn){
        this.clubIdx = clubIdx;
        this.clubName = clubName;
        this.clubInfo = clubInfo;
        this.clubCity = clubCity;
        this.clubCounty = clubCounty;
        this.useYn = useYn;
    }

    public Club dtoToClubEntity(){
        return Club.builder()
            .clubIdx(clubIdx)
            .clubName(clubName)
            .clubInfo(clubInfo)
            .clubCity(clubCity)
            .clubCounty(clubCounty)
            .useYn(useYn).build();
    }
}

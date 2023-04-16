package com.tennisPartner.tennisP.club.domain;

import com.tennisPartner.tennisP.common.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="club_tb")
@DynamicUpdate
public class Club extends BaseTimeEntity {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long clubIdx;
    @Column(unique = true, nullable = false)
    private String clubName;
    @Column(nullable = false)
    private String clubInfo;
    @Column(nullable = false)
    private String clubCity;
    @Column(nullable = false)
    private String clubCounty;
    @Column(nullable = false)
    private char useYn;

    @Builder
    public Club(Long clubIdx, String clubName, String clubInfo, String clubCity, String clubCounty ,char useYn){
        this.clubIdx = clubIdx;
        this.clubName = clubName;
        this.clubInfo = clubInfo;
        this.clubCity = clubCity;
        this.clubCounty = clubCounty;
        this.useYn = useYn;
    }

    @PrePersist
    public void prePersist(){
        this.useYn = 'Y';
    }

    public void updateClub(Club Entity){
        this.clubName = Entity.getClubName();
        this.clubInfo = Entity.getClubInfo();
        this.clubCity = Entity.getClubCity();
        this.clubCounty = Entity.getClubCounty();
        this.useYn = Entity.getUseYn();
    }
}

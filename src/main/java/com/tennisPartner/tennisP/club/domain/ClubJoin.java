package com.tennisPartner.tennisP.club.domain;

import com.tennisPartner.tennisP.common.BaseTimeEntity;
import com.tennisPartner.tennisP.user.entity.User;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name="club_join_tb")
public class ClubJoin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long clubJoinIdx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="club_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private String clubGrade;
    private char useYn;

    public ClubJoin(Club club, User user, String clubGrade){
        this.club = club;
        this.user = user;
        this.clubGrade = clubGrade;
    }

    @PrePersist
    public void prePersist(){
        this.useYn = this.useYn == 0 ? 'Y' : this.useYn;
    }

    public void leaveClub(){
        this.useYn = 'N';
    }

}

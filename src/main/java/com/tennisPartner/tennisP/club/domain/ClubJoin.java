package com.tennisPartner.tennisP.club.domain;

import com.tennisPartner.tennisP.common.domain.BaseTimeEntity;
import com.tennisPartner.tennisP.user.domain.User;
import javax.persistence.Column;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="club_join_tb")
@DynamicUpdate
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

    @Column(nullable = false)
    private String clubGrade;
    @Column(nullable = false)
    private char useYn;

    @Builder
    public ClubJoin(Club club, User user, String clubGrade){
        this.club = club;
        this.user = user;
        this.clubGrade = clubGrade;
    }

    @PrePersist
    public void prePersist(){
        this.useYn = 'Y';
    }

    public void leaveClub(){
        this.useYn = 'N';
    }

    public void reJoinClub(){
        this.useYn = 'Y';

    }

}

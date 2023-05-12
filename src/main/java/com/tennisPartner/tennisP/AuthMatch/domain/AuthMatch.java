package com.tennisPartner.tennisP.AuthMatch.domain;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.common.domain.CreateTimeEntity;
import com.tennisPartner.tennisP.user.domain.User;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="auth_match_tb")
@DynamicUpdate
public class AuthMatch extends CreateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authMatchIdx;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="club_board_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ClubBoard clubBoard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="owner_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User owner;
    private String matchType;
    private int userCnt;
    private int gameCnt;
    private int courtCnt;

    @Builder
    public AuthMatch(Long authMatchIdx, ClubBoard clubBoard, User owner, String matchType, int userCnt, int gameCnt, int courtCnt){
        this.authMatchIdx = authMatchIdx;
        this.clubBoard = clubBoard;
        this.owner = owner;
        this.matchType = matchType;
        this.userCnt = userCnt;
        this.gameCnt = gameCnt;
        this.courtCnt = courtCnt;
    }

}

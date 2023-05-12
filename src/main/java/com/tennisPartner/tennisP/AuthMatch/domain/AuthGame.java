package com.tennisPartner.tennisP.AuthMatch.domain;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="auth_game_tb")
@DynamicUpdate
public class AuthGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authGameIdx;
    @ManyToOne
    @JoinColumn(name="auth_match_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AuthMatch authMatch;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="host_idx1", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User host1;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="host_idx2", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User host2;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="guest_idx1", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User guest1;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="guest_idx2", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User guest2;
    private int courtSequence;
    private int hostScore;
    private int guestScore;

    @Builder
    public AuthGame(Long authGameIdx, AuthMatch authMatch, User host1, User host2, User guest1, User guest2, int courtSequence, int hostScore, int guestScore){
        this.authGameIdx = authGameIdx;
        this.authMatch = authMatch;
        this.host1 = host1;
        this.host2 = host2;
        this.guest1 = guest1;
        this.guest2 = guest2;
        this.courtSequence = courtSequence;
        this.hostScore = hostScore;
        this.guestScore = guestScore;
    }

    public void updateAuthGame(AuthGame req){
        this.host1 = req.getHost1();
        this.host2 = req.getHost2();
        this.guest1 = req.getGuest1();
        this.guest2 = req.getGuest2();
        this.hostScore = req.getHostScore();
        this.guestScore = req.getGuestScore();
    }

}
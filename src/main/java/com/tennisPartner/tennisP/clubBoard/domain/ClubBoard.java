package com.tennisPartner.tennisP.clubBoard.domain;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.common.domain.BaseTimeEntity;
import com.tennisPartner.tennisP.user.domain.User;
import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity(name="club_board_tb")
public class ClubBoard extends BaseTimeEntity {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long clubBoardIdx;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="club_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Club club;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="writer_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User writer;

    @Column(nullable = false)
    private char clubBoardType;
    @Column(nullable = false)
    private String clubBoardTitle;
    @Column(nullable = false)
    private String clubBoardContents;

    @Column(nullable = false)
    private int wantedCnt;
    @Column(nullable = false)
    private char useYn;

    private LocalDateTime meetDt;

    @PrePersist
    public void prePersist(){
        this.useYn = 'Y';
    }


    public void updateClubBoard(ClubBoard Entity){
        this.clubBoardType= Entity.getClubBoardType();
        this.clubBoardTitle = Entity.getClubBoardTitle();
        this.clubBoardContents = Entity.getClubBoardContents();
        this.wantedCnt = Entity.getWantedCnt();
        this.meetDt = Entity.getMeetDt();
        this.useYn = Entity.getUseYn();
    }
}

package com.tennisPartner.tennisP.clubBoard.domain;

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
@Entity(name="club_board_join_tb")
public class ClubBoardJoin extends CreateTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubBoardJoinIdx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="club_board_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ClubBoard clubBoard;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    public ClubBoardJoin(ClubBoard clubBoard, User user){
        this.clubBoard = clubBoard;
        this.user = user;
    }
}

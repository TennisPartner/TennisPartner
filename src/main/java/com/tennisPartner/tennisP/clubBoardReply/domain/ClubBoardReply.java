package com.tennisPartner.tennisP.clubBoardReply.domain;


import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="club_board_reply_tb")
@DynamicUpdate
public class ClubBoardReply extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubBoardReplyIdx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="board_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ClubBoard clubBoard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="writer_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User writer;

    @Column(nullable = false)
    private String replyContents;

    @Builder
    public ClubBoardReply(Long clubBoardReplyIdx, ClubBoard clubBoard, User writer, String replyContents){
        this.clubBoardReplyIdx = clubBoardReplyIdx;
        this.clubBoard = clubBoard;
        this.writer = writer;
        this.replyContents = replyContents;
    }

    public void updateClubBoardReply(String replyContents){
        this.replyContents = replyContents;
    }
}

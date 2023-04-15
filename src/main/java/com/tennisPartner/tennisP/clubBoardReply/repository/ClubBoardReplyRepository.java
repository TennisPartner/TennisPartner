package com.tennisPartner.tennisP.clubBoardReply.repository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoardReply.domain.ClubBoardReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBoardReplyRepository extends JpaRepository<ClubBoardReply, Long> {
    Page<ClubBoardReply> findByClubBoard(ClubBoard clubBoard, Pageable pageable);
}

package com.tennisPartner.tennisP.clubBoard.repository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBoardJoinRepository extends JpaRepository<ClubBoardJoin, Long> {
    Optional<ClubBoardJoin> findByUserAndClubBoard(User user, ClubBoard clubBoard);
    Long countByClubBoard(ClubBoard clubBoard);
    Optional<List<ClubBoardJoin>> findByClubBoard(ClubBoard clubBoard);
}

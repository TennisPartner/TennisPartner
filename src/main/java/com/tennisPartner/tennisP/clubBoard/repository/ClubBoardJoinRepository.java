package com.tennisPartner.tennisP.clubBoard.repository;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoardJoin;
import com.tennisPartner.tennisP.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBoardJoinRepository extends JpaRepository<ClubBoardJoin, Long> {
    public Optional<ClubBoardJoin> findByUserAndClubBoard(User user, ClubBoard clubBoard);
    public Long countByClubBoard(ClubBoard clubBoard);
}

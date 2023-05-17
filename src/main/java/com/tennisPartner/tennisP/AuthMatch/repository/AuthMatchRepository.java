package com.tennisPartner.tennisP.AuthMatch.repository;

import com.tennisPartner.tennisP.AuthMatch.domain.AuthMatch;
import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthMatchRepository extends JpaRepository<AuthMatch, Long> {

    Optional<AuthMatch> findByClubBoard(ClubBoard clubBoard);
}

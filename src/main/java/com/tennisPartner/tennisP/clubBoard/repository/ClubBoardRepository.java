package com.tennisPartner.tennisP.clubBoard.repository;

import com.tennisPartner.tennisP.clubBoard.domain.ClubBoard;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBoardRepository extends JpaRepository<ClubBoard,Long> {

    public Optional<Page<ClubBoard>> findByUseYn(char useYn, Pageable pageable);
}

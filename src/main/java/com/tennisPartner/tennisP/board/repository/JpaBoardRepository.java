package com.tennisPartner.tennisP.board.repository;

import com.tennisPartner.tennisP.board.domain.Board;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {

    Optional<Page<Board>> findByUseYn(String useYn, Pageable pageable);
}

package com.tennisPartner.tennisP.board.repository;

import com.tennisPartner.tennisP.board.domain.Board;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardDSLRepository{

    Optional<Page<Board>> findByUseYn(String useYn, Pageable pageable);

    Optional<Board> findByUseYnAndBoardIdx(String useYn, Long boardIdx);
}

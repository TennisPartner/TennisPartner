package com.tennisPartner.tennisP.board.repository;

import com.tennisPartner.tennisP.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {
}

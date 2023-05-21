package com.tennisPartner.tennisP.board.repository;

import com.tennisPartner.tennisP.board.domain.Board;
import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardDSLRepository {

    Page<Board> getBoardList(BoardSearchCondition cond, Pageable pageable);

}

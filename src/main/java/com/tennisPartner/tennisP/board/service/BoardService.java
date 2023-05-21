package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.board.repository.dto.UpdateBoardRequestDto;
import org.springframework.data.domain.Page;

public interface BoardService {
    Long createBoard(CreateBoardRequestDto createBoardRequestDto, Long userIdx);

    Page<GetBoardResponseDto> getBoardList(BoardSearchCondition cond, Integer page, Integer size);

    GetBoardResponseDto getBoard(Long boardIdx);

    boolean updateBoard(Long boardIdx, Long userIdx, UpdateBoardRequestDto updateBoardRequestDto);

}

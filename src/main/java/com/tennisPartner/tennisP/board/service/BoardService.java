package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;

public interface BoardService {
    Long createBoard(CreateBoardRequestDto createBoardRequestDto, Long userIdx);
}

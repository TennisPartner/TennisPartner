package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {
    Long createBoard(CreateBoardRequestDto createBoardRequestDto, Long userIdx);

    Page<GetBoardResponseDto> getBoardList(Integer page, Integer size);

}

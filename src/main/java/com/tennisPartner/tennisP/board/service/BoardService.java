package com.tennisPartner.tennisP.board.service;

import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.board.repository.dto.UpdateBoardRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    Long createBoard(CreateBoardRequestDto createBoardRequestDto, List<MultipartFile> boardPhotos, Long userIdx) throws IOException;

    Page<GetBoardResponseDto> getBoardList(BoardSearchCondition cond, Integer page, Integer size);

    GetBoardResponseDto getBoard(Long boardIdx);

    boolean updateBoard(Long boardIdx, Long userIdx, UpdateBoardRequestDto updateBoardRequestDto);

}

package com.tennisPartner.tennisP.board.controller;

import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.board.service.BoardService;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/boards")
    public ResponseEntity getBoardList(@RequestParam(required = false) Integer page) {
        if (page == null) {
            page = 1;
        }

        Page<GetBoardResponseDto> resList = boardService.getBoardList(page, 5);

        return null;
    }

    @PostMapping("/login/api/boards")
    public ResponseEntity createBoard(@LoginMemberId Long userIdx,
                                      @RequestBody @Validated CreateBoardRequestDto createBoardRequestDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Long boardIdx = boardService.createBoard(createBoardRequestDto, userIdx);
        return new ResponseEntity(boardIdx, HttpStatus.OK);
    }

}

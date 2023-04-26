package com.tennisPartner.tennisP.board.controller;

import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.service.BoardService;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity getBoardList(@RequestParam(required = false) Integer page) {
        return null;
    }

    @PostMapping
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

package com.tennisPartner.tennisP.board.controller;

import com.tennisPartner.tennisP.board.repository.dto.BlobTestDto;
import com.tennisPartner.tennisP.board.repository.dto.BoardSearchCondition;
import com.tennisPartner.tennisP.board.repository.dto.CreateBoardRequestDto;
import com.tennisPartner.tennisP.board.repository.dto.GetBoardResponseDto;
import com.tennisPartner.tennisP.board.repository.dto.UpdateBoardRequestDto;
import com.tennisPartner.tennisP.board.service.BoardService;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import io.swagger.models.Response;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/boards")
    public ResponseEntity getBoardList(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) BoardSearchCondition cond
    ) {
        if (page == null) {
            page = 0;
        }

        Page<GetBoardResponseDto> resList = boardService.getBoardList(cond, page, 5);

        return null;
    }

    @PostMapping(value = "/login/api/boards",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createBoard(@LoginMemberId Long userIdx,
                                      @RequestPart @Validated CreateBoardRequestDto createBoardRequestDto,
                                      @RequestPart(required = false) List<MultipartFile> boardPhotos,
                                      BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Long boardIdx = boardService.createBoard(createBoardRequestDto, boardPhotos, userIdx);
        return new ResponseEntity(boardIdx, HttpStatus.OK);
    }

    @GetMapping("/login/api/boards/{boardIdx}")
    public ResponseEntity getBoard(@PathVariable Long boardIdx) {

        GetBoardResponseDto board = boardService.getBoard(boardIdx);

        if (board == null) {
            return new ResponseEntity("게시판 조회를 실패 하였습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(board, HttpStatus.OK);
    }


    @PatchMapping("/login/api/boards/{boardIdx}")
    public ResponseEntity updateBoard(@PathVariable Long boardIdx,
                                      @LoginMemberId Long userIdx,
                                      @RequestPart @Validated UpdateBoardRequestDto updateBoardRequestDto,
                                      @RequestPart(required = false) List<MultipartFile> boardPhotos,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        boolean deleteBoardTf = boardService.updateBoard(boardIdx, userIdx, updateBoardRequestDto, boardPhotos);

        if (!deleteBoardTf) {
            throw new CustomException("잘못된 게시물에 대한 접근입니다.", 401);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/blob/test")
    public ResponseEntity blobTest(@RequestBody BlobTestDto blobs) throws IOException {
        boardService.blobTest(blobs.getPhotos());
        return new ResponseEntity(HttpStatus.OK);
    }


}

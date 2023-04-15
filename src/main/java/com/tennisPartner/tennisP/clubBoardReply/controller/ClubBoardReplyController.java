package com.tennisPartner.tennisP.clubBoardReply.controller;

import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
import com.tennisPartner.tennisP.clubBoardReply.service.ClubBoardReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/api/clubs/{clubIdx}/boards/{clubBoardIdx}")
public class ClubBoardReplyController {
    ClubBoardReplyService clubBoardReplyService;

    @Autowired
    public ClubBoardReplyController(ClubBoardReplyService clubBoardReplyService){
        this.clubBoardReplyService = clubBoardReplyService;
    }
    @PostMapping("/replys")
    public ResponseEntity<ClubBoardReplyResponseDTO> createClubBoardReply(@PathVariable Long clubIdx,
        @PathVariable Long clubBoardIdx, @RequestBody String replyContents){
        if(clubIdx == 0){
            return new ResponseEntity("클럽 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(clubBoardIdx == 0){
            return new ResponseEntity("클럽 게시판 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(replyContents == null || replyContents.isBlank()){
            return new ResponseEntity("내용을 입력해주세요.",HttpStatus.BAD_REQUEST);
        }
        ClubBoardReplyResponseDTO res = clubBoardReplyService.createClubBoardReply(clubIdx, clubBoardIdx, replyContents);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PutMapping("/replys/{clubBoardReplyIdx}")
    public ResponseEntity<ClubBoardReplyResponseDTO> updateClubBoardReply(@PathVariable Long clubIdx,
        @PathVariable Long clubBoardIdx, @PathVariable Long clubBoardReplyIdx, @RequestBody String replyContents){
        if(clubIdx == 0){
            return new ResponseEntity("클럽 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(clubBoardIdx == 0){
            return new ResponseEntity("클럽 게시판 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(replyContents == null || replyContents.isBlank()){
            return new ResponseEntity("내용을 입력해주세요.",HttpStatus.BAD_REQUEST);
        }

        ClubBoardReplyResponseDTO res = clubBoardReplyService.updateClubBoardReply(clubIdx, clubBoardIdx,
            clubBoardReplyIdx, replyContents );

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/replys/{clubBoardReplyIdx}")
    public ResponseEntity deleteClubBoardReply(@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @PathVariable Long clubBoardReplyIdx){
        if(clubIdx == 0){
            return new ResponseEntity("클럽 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(clubBoardIdx == 0){
            return new ResponseEntity("클럽 게시판 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }

        clubBoardReplyService.deleteClubBoardReply(clubIdx, clubBoardIdx, clubBoardReplyIdx);

        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/replys")
    public ResponseEntity<Page<ClubBoardReplyResponseDTO>> getClubBoardReplyList(@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @RequestParam(value="page", defaultValue = "0") int page){
        if(clubIdx == 0){
            return new ResponseEntity("클럽 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if(clubBoardIdx == 0){
            return new ResponseEntity("클럽 게시판 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }

        Page<ClubBoardReplyResponseDTO> resList = clubBoardReplyService.getClubBoardReplyList(clubIdx, clubBoardIdx, page, 5);

        return new ResponseEntity<>(resList, HttpStatus.OK);
    }

}

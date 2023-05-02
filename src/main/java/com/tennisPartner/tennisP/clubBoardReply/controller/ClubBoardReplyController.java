package com.tennisPartner.tennisP.clubBoardReply.controller;

import com.tennisPartner.tennisP.clubBoardReply.repository.dto.ClubBoardReplyResponseDTO;
import com.tennisPartner.tennisP.clubBoardReply.service.ClubBoardReplyService;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
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
    public ResponseEntity<ClubBoardReplyResponseDTO> createClubBoardReply(@LoginMemberId Long userIdx, @PathVariable Long clubIdx,
        @PathVariable Long clubBoardIdx, @RequestBody String replyContents){

        ClubBoardReplyResponseDTO res = clubBoardReplyService.createClubBoardReply(clubIdx, clubBoardIdx, replyContents, userIdx);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PutMapping("/replys/{clubBoardReplyIdx}")
    public ResponseEntity<ClubBoardReplyResponseDTO> updateClubBoardReply(@LoginMemberId Long userIdx,@PathVariable Long clubIdx,
        @PathVariable Long clubBoardIdx, @PathVariable Long clubBoardReplyIdx, @RequestBody String replyContents){


        ClubBoardReplyResponseDTO res = clubBoardReplyService.updateClubBoardReply(clubIdx, clubBoardIdx,
            clubBoardReplyIdx, replyContents, userIdx );

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/replys/{clubBoardReplyIdx}")
    public ResponseEntity deleteClubBoardReply(@LoginMemberId Long userIdx,@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @PathVariable Long clubBoardReplyIdx){


        clubBoardReplyService.deleteClubBoardReply(clubIdx, clubBoardIdx, clubBoardReplyIdx, userIdx);

        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/replys")
    public ResponseEntity<Page<ClubBoardReplyResponseDTO>> getClubBoardReplyList(@LoginMemberId Long userIdx,@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @RequestParam(value="page", defaultValue = "0") int page){

        Page<ClubBoardReplyResponseDTO> resList = clubBoardReplyService.getClubBoardReplyList(clubIdx, clubBoardIdx, page, 5, userIdx);

        return new ResponseEntity<>(resList, HttpStatus.OK);
    }

}

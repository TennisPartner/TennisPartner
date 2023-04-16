package com.tennisPartner.tennisP.clubBoard.controller;


import com.tennisPartner.tennisP.club.repository.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import com.tennisPartner.tennisP.clubBoard.service.ClubBoardService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/api/clubs/{clubIdx}/boards")
public class ClubBoardController {

    ClubBoardService clubBoardService;

    @Autowired
    public ClubBoardController(ClubBoardService clubBoardService){
        this.clubBoardService = clubBoardService;
    }
    @PostMapping("")
    public ResponseEntity<ClubBoardResponseDTO> createClubBoard(@PathVariable Long clubIdx,
        @RequestBody @Valid ClubBoardRequestDTO req , BindingResult bindingResult){

        if(clubIdx == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }

        ClubBoardResponseDTO res = clubBoardService.createClubBoard(clubIdx, req);

        return new ResponseEntity(res, HttpStatus.OK);
    }
    @PatchMapping("/{clubBoardIdx}")
    public ResponseEntity<ClubBoardResponseDTO> updateClubBoard(@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @RequestBody @Valid ClubBoardRequestDTO req, BindingResult bindingResult){
        if(clubIdx == 0 || clubBoardIdx == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }

        ClubBoardResponseDTO res = clubBoardService.updateClubBoard(clubIdx, clubBoardIdx, req);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<ClubBoardResponseDTO>> getClubBoardList(@PathVariable Long clubIdx, @RequestParam(value="page") int page){
        if(clubIdx == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<ClubBoardResponseDTO> resList = clubBoardService.getClubBoardList(clubIdx,page,5);

        return new ResponseEntity(resList, HttpStatus.OK);
    }

    @GetMapping("/{clubBoardIdx}")
    public ResponseEntity<ClubBoardResponseDTO> getClubBoard(@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx){

        if(clubIdx == 0 || clubBoardIdx == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClubBoardResponseDTO res = clubBoardService.getClubBoard(clubIdx, clubBoardIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PostMapping("/{clubBoardIdx}/join")
    public ResponseEntity<ClubBoardJoinResponseDTO> joinClubBoard(@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx){
        if(clubIdx == 0 || clubBoardIdx == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClubBoardJoinResponseDTO res = clubBoardService.joinMatch(clubIdx, clubBoardIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @DeleteMapping("/{clubBoardIdx}/join")
    public ResponseEntity leaveClubBoard(@PathVariable Long clubIdx, @PathVariable Long clubBoardIdx){
        if(clubIdx == 0 || clubBoardIdx == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        clubBoardService.leaveMatch(clubIdx, clubBoardIdx);

        return new ResponseEntity(HttpStatus.OK);
    }
}

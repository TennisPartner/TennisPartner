package com.tennisPartner.tennisP.club.controller;
import com.tennisPartner.tennisP.club.repository.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.service.ClubService;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login/api/clubs")
public class ClubController {
    ClubService clubService;


    public ClubController(ClubService clubService){
        this.clubService = clubService;

    }


    @PostMapping(value="")
    public ResponseEntity createClub(@LoginMemberId Long userIdx, @RequestBody @Valid ClubRequestDTO req, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        ClubResponseDTO res = clubService.createClub(req, userIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PatchMapping(value="{clubIdx}")
    public ResponseEntity updateClub(@LoginMemberId Long userIdx, @PathVariable Long clubIdx, @RequestBody @Valid ClubRequestDTO req, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        req.setClubIdx(clubIdx);
        ClubResponseDTO res = clubService.updateClub(clubIdx,req, userIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }
    @GetMapping(value="")
    public ResponseEntity getClubList(@RequestParam(value="page") int page, @RequestParam(value="type",required = false) String type,
        @RequestParam(value="condition", required = false) String condition){
        if(type == null) type = "";
        if(condition == null) condition = "";


        Page<ClubResponseDTO> resList = clubService.getClubList(page, 5, type, condition);

        return new ResponseEntity(resList, HttpStatus.OK);
    }

    @GetMapping(value="{clubIdx}")
    public ResponseEntity getClub(@PathVariable Long clubIdx) {

        ClubResponseDTO res = clubService.getClub(clubIdx);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PostMapping(value="{clubIdx}/join")
    public ResponseEntity joinClub(@LoginMemberId Long userIdx,@PathVariable Long clubIdx){

        ClubJoinResponseDTO res = clubService.joinClub(clubIdx, userIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PatchMapping(value="{clubIdx}/join")
    public ResponseEntity leaveClub(@LoginMemberId Long userIdx,@PathVariable Long clubIdx){

        clubService.leaveClub(clubIdx, userIdx);

        return new ResponseEntity(HttpStatus.OK);
    }




}

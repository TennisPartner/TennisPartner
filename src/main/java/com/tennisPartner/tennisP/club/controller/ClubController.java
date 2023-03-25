package com.tennisPartner.tennisP.club.controller;
import com.tennisPartner.tennisP.club.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.dto.ClubResponseDTO;
import com.tennisPartner.tennisP.club.service.ClubService;
import javax.validation.Valid;
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

@RestController
@RequestMapping("/login/api/clubs")
public class ClubController {
    ClubService clubService;

    public ClubController(ClubService clubService){
        this.clubService = clubService;
    }

    @PostMapping(value="")
    public ResponseEntity createClub(@RequestBody @Valid ClubRequestDTO req, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }

        ClubResponseDTO res = clubService.createClub(req);
        System.out.println("res : " + res);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PatchMapping(value="{clubIdx}")
    public ResponseEntity updateClub(@PathVariable Long clubIdx, @RequestBody @Valid ClubRequestDTO req, BindingResult bindingResult){
        if(clubIdx == 0 || clubIdx == null){
            return new ResponseEntity("없는 클럽 입니다.",HttpStatus.BAD_REQUEST);
        }
        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        req.setClubIdx(clubIdx);
        ClubResponseDTO res = clubService.updateClub(clubIdx,req);

        return new ResponseEntity(res, HttpStatus.OK);
    }
    @GetMapping(value="")
    public ResponseEntity getClubList(@RequestParam(value="page") int page){

        Page<ClubResponseDTO> resList = clubService.getClubList(page, 5);

        return new ResponseEntity(resList, HttpStatus.OK);
    }

    @GetMapping(value="{clubIdx}")
    public ResponseEntity getClub(@PathVariable Long clubIdx) {

        if(clubIdx == 0){
            return new ResponseEntity("없는 클럽 입니다.",HttpStatus.BAD_REQUEST);
        }

        ClubResponseDTO res = clubService.getClub(clubIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PostMapping(value="{clubIdx}/join")
    public ResponseEntity joinClub(@PathVariable Long clubIdx){
        if(clubIdx == 0 ){
            return new ResponseEntity("없는 클럽 입니다.",HttpStatus.BAD_REQUEST);
        }

        ClubJoinResponseDTO res = clubService.joinClub(clubIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }


}

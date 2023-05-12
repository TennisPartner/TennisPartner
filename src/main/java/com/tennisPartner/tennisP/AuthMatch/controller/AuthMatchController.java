package com.tennisPartner.tennisP.AuthMatch.controller;

import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthGameResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.MatchResultResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.PlayCountResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.UpdateAuthGameRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.service.AuthMatchService;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import io.swagger.models.Response;
import java.util.List;
import java.util.regex.MatchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login/api/clubs{clubIdx}/clubBoards/{clubBoardIdx}/matches")
public class AuthMatchController {
    AuthMatchService authMatchService;

    @Autowired
    public AuthMatchController(AuthMatchService authMatchService){
        this.authMatchService = authMatchService;
    }

    @PostMapping(value="")
    public ResponseEntity<AuthMatchResponseDTO> createMatch(@LoginMemberId Long userIdx, @PathVariable Long clubIdx,
        @PathVariable Long clubBoardIdx, @RequestBody AuthMatchRequestDTO req){

        AuthMatchResponseDTO res = authMatchService.createMatch(userIdx, clubIdx, clubBoardIdx, req);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping("/{authMatchIdx}")
    public ResponseEntity<List<AuthGameResponseDTO>> getGame(@LoginMemberId Long userIdx, @PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @PathVariable Long authMatchIdx){

        List<AuthGameResponseDTO> gameList = authMatchService.getGame(userIdx,clubIdx,clubBoardIdx,authMatchIdx);

        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @PatchMapping("/{authMatchIdx}/games/{authGameIdx}")
    public ResponseEntity<AuthGameResponseDTO> updateGame(@LoginMemberId Long userIdx, @PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @PathVariable Long authMatchIdx, @PathVariable Long authGameIdx, @RequestBody
        UpdateAuthGameRequestDTO req){

        AuthGameResponseDTO game = authMatchService.updateGame(userIdx, clubIdx, clubBoardIdx, authMatchIdx, authGameIdx, req);

        return new ResponseEntity<>(game, HttpStatus.OK);
    }
    @GetMapping("/{authMatchIdx}/result")
    public ResponseEntity<List<MatchResultResponseDTO>> getResult(@LoginMemberId Long userIdx, @PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @PathVariable Long authMatchIdx){
        List<MatchResultResponseDTO> resList = authMatchService.getResult(userIdx, clubIdx, clubBoardIdx, authMatchIdx);
        return new ResponseEntity<>(resList, HttpStatus.OK);
    }
    @GetMapping("/{authMatchIdx}/count")
    public ResponseEntity<List<PlayCountResponseDTO>> getPlayCount(@LoginMemberId Long userIdx, @PathVariable Long clubIdx, @PathVariable Long clubBoardIdx,
        @PathVariable Long authMatchIdx){
        List<PlayCountResponseDTO> resList = authMatchService.getGameCnt(userIdx, clubIdx, clubBoardIdx, authMatchIdx);
        return new ResponseEntity<>(resList, HttpStatus.OK);
    }
}

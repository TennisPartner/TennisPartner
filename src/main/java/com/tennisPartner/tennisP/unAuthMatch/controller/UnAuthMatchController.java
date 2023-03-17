package com.tennisPartner.tennisP.unAuthMatch.controller;

import com.tennisPartner.tennisP.unAuthMatch.dto.UnAuthMatchRequestDTO;
import com.tennisPartner.tennisP.unAuthMatch.service.UnAuthMatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matchs")
public class UnAuthMatchController {

    UnAuthMatchService unAuthMatchService;

    @Autowired
    public UnAuthMatchController(UnAuthMatchService unAuthMatchService){
        this.unAuthMatchService = unAuthMatchService;
    }

    @PostMapping()
    public ResponseEntity<Map<String,List<List<Integer>>>> unAuthMatching(@RequestBody UnAuthMatchRequestDTO req){

        int playerCnt = req.getPlayerCnt();
        int gameCnt = req.getGameCnt();
        int courtCnt = req.getCourtCnt();
        if(playerCnt < 4){
            return new ResponseEntity("4명 이상 모여야 사용 가능합니다.",HttpStatus.BAD_REQUEST);
        }
        if(gameCnt * 4 < playerCnt){
            return new ResponseEntity("1게임도 못하는 사람이 있습니다.", HttpStatus.BAD_REQUEST);
        }

        if(courtCnt * 4 > playerCnt){
            courtCnt = courtCnt - (courtCnt-playerCnt/4);
            req.setCourtCnt(courtCnt);
        }

        List<List<Integer>> totalList = unAuthMatchService.unAuthMatching(req);
        System.out.println(totalList);
        Map<String, List<List<Integer>>> result = new HashMap<>();
        result.put("gameList", totalList);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}

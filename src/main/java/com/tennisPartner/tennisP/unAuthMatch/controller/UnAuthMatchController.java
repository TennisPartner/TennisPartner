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
    public ResponseEntity<Map<String,Object>> unAuthMatching(@RequestBody UnAuthMatchRequestDTO req){

        int playerCnt = req.getPlayerCnt();
        int gameCnt = req.getGameCnt();
        int courtCnt = req.getCourtCnt();
        String message = "";
        if(playerCnt < 4){
            message = "4명 이상 모여야 사용 가능합니다. 인원수를 증가시키세요";
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }
        if(gameCnt * 4 < playerCnt){
            message = "1게임도 못하는 사람이 있습니다. 게임수를 증가시키거나, 인원수를 줄여주세요";
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }

        if(courtCnt * 4 > playerCnt){
            courtCnt = courtCnt - (courtCnt-playerCnt/4);
            req.setCourtCnt(courtCnt);
            message = "코트에비해 사람이 적어 코트 수를 " + courtCnt + "로 감소하였습니다.";
        }

        List<List<Integer>> totalList = unAuthMatchService.unAuthMatching(req);
        Map<String, Object> result = new HashMap<>();
        result.put("gameList", totalList);
        result.put("message", message);


        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}

package com.tennisPartner.tennisP.unAuthMatch.service;

import com.tennisPartner.tennisP.unAuthMatch.dto.UnAuthMatchRequestDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class UnAuthMatchServiceImpl implements UnAuthMatchService {


    @Override
    public List<List<Integer>> unAuthMatching(UnAuthMatchRequestDTO req) {
        int playerCnt = req.getPlayerCnt();
        int gameCnt = req.getGameCnt();
        int courtCnt = req.getCourtCnt();
        List<List<Integer>> totallist = new ArrayList<>();

        List<Integer> gamerlist = new ArrayList<>();
        List<Integer> gamelist = new ArrayList<>();

        int totalGamer = gameCnt * 4;
        for (int i = 0; i < totalGamer; i++) {
            gamerlist.add(i % playerCnt);
        }
        for (int i = 0; i < gameCnt / courtCnt + 1; i++) {
            gamelist = new ArrayList<>();
            for (int j = 0; j < 4 * courtCnt; j++) {
                try {
                    gamelist.add(gamerlist.get(4 * courtCnt * i + j));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }

            }
            if(!gamelist.isEmpty())
            totallist.add(gamelist);

        }
        for (int i = 0; i < totallist.size(); i++) {
            Collections.shuffle(totallist.get(i));

        }
        return totallist;
    }

}

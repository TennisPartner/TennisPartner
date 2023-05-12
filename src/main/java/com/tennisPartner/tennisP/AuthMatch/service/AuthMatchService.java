package com.tennisPartner.tennisP.AuthMatch.service;

import com.tennisPartner.tennisP.AuthMatch.repository.dto.PlayCountResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.UpdateAuthGameRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthGameResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchRequestDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.AuthMatchResponseDTO;
import com.tennisPartner.tennisP.AuthMatch.repository.dto.MatchResultResponseDTO;
import java.util.List;

public interface AuthMatchService {

    AuthMatchResponseDTO createMatch(Long userIdx, Long clubIdx, Long clubBoardIdx, AuthMatchRequestDTO req);
    List<PlayCountResponseDTO>  getGameCnt(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx);
    List<AuthGameResponseDTO> getGame(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx);
    AuthGameResponseDTO updateGame(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx, Long authGameIdx, UpdateAuthGameRequestDTO req);
    List<MatchResultResponseDTO> getResult(Long userIdx, Long clubIdx, Long clubBoardIdx, Long authMatchIdx);
}

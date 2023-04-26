package com.tennisPartner.tennisP.clubBoard.service;

import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import org.springframework.data.domain.Page;

public interface ClubBoardService {
    public ClubBoardResponseDTO createClubBoard(Long clubIdx, ClubBoardRequestDTO req, Long userIdx);
    public ClubBoardResponseDTO updateClubBoard(Long clubIdx, Long clubBoardIdx, ClubBoardRequestDTO req, Long userIdx);
    public Page<ClubBoardResponseDTO> getClubBoardList(Long clubIdx, int page, int size, Long userIdx);
    public ClubBoardResponseDTO getClubBoard(Long clubIdx, Long clubBoardIdx, Long userIdx);
    public ClubBoardJoinResponseDTO joinMatch(Long clubIdx, Long clubBoardIdx, Long userIdx);
    public void leaveMatch(Long clubIdx, Long clubBoardIdx, Long userIdx);

}

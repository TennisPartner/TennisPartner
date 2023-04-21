package com.tennisPartner.tennisP.clubBoard.service;

import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardJoinResponseDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardRequestDTO;
import com.tennisPartner.tennisP.clubBoard.repository.dto.ClubBoardResponseDTO;
import org.springframework.data.domain.Page;

public interface ClubBoardService {
    public ClubBoardResponseDTO createClubBoard(Long clubIdx, ClubBoardRequestDTO req);
    public ClubBoardResponseDTO updateClubBoard(Long clubIdx, Long clubBoardIdx, ClubBoardRequestDTO req);
    public Page<ClubBoardResponseDTO> getClubBoardList(Long clubIdx, int page, int size);
    public ClubBoardResponseDTO getClubBoard(Long clubIdx, Long clubBoardIdx);
    public ClubBoardJoinResponseDTO joinMatch(Long clubIdx, Long clubBoardIdx);
    public void leaveMatch(Long clubIdx, Long clubBoardIdx);

}

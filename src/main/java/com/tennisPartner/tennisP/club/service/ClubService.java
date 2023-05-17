package com.tennisPartner.tennisP.club.service;

import com.tennisPartner.tennisP.club.repository.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.repository.dto.ClubResponseDTO;
import org.springframework.data.domain.Page;

public interface ClubService {
    public ClubResponseDTO createClub(ClubRequestDTO req, Long userIdx);
    public ClubResponseDTO updateClub(Long clubIdx, ClubRequestDTO req, Long userIdx);
    public Page<ClubResponseDTO> getClubList(int page, int size, String type, String condition);
    public ClubResponseDTO getClub(Long clubIdx);
    public ClubJoinResponseDTO joinClub(Long clubIdx, Long userIdx);
    public void leaveClub(Long clubIdx, Long userIdx);

}

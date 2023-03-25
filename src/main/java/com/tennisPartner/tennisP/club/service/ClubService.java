package com.tennisPartner.tennisP.club.service;

import com.tennisPartner.tennisP.club.dto.ClubJoinResponseDTO;
import com.tennisPartner.tennisP.club.dto.ClubRequestDTO;
import com.tennisPartner.tennisP.club.dto.ClubResponseDTO;
import org.springframework.data.domain.Page;

public interface ClubService {
    public ClubResponseDTO createClub(ClubRequestDTO req);
    public ClubResponseDTO updateClub(Long clubIdx, ClubRequestDTO req);
    public Page<ClubResponseDTO> getClubList(int page, int size);
    public ClubResponseDTO getClub(Long clubIdx);
    public ClubJoinResponseDTO joinClub(Long clubIdx);
}

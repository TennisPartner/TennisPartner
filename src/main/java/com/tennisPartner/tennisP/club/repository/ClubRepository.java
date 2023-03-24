package com.tennisPartner.tennisP.club.repository;

import com.tennisPartner.tennisP.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    public Page<Club> findByUseYn(char useYn, Pageable pageable);
    public Club findByClubName(String clubName);
}

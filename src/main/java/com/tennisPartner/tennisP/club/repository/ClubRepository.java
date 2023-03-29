package com.tennisPartner.tennisP.club.repository;

import com.tennisPartner.tennisP.club.domain.Club;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    public Optional<Page<Club>> findByUseYn(char useYn, Pageable pageable);
    public Optional<Club> findByClubName(String clubName);
}

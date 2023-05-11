package com.tennisPartner.tennisP.club.repository;

import com.tennisPartner.tennisP.club.domain.Club;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    public Optional<Club> findByClubName(String clubName);
}

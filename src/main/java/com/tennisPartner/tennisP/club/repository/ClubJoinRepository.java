package com.tennisPartner.tennisP.club.repository;

import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.club.domain.ClubJoin;
import com.tennisPartner.tennisP.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubJoinRepository extends JpaRepository<ClubJoin,Long> {


    public Optional<ClubJoin> findByUserAndClub(User user, Club club);
}

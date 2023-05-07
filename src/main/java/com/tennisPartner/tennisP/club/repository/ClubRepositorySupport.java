package com.tennisPartner.tennisP.club.repository;

import com.tennisPartner.tennisP.club.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubRepositorySupport {

    public Page<Club> findByCondition(String type, String condition, Pageable pageable);
}

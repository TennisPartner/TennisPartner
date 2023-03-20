package com.tennisPartner.tennisP.user.repository;

import com.tennisPartner.tennisP.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
}

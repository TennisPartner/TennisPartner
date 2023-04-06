package com.tennisPartner.tennisP.user.service;


import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;

public interface UserService {

    User join(JoinRequestDto join);
}

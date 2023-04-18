package com.tennisPartner.tennisP.user.service;


import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;

public interface UserService {

    User join(JoinRequestDto join);

    LoginResponseDto login(LoginRequestDto login);

}

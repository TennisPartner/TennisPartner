package com.tennisPartner.tennisP.user.service;


import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;

public interface UserService {

    User join(JoinRequestDto join);
    LoginResponseDto login(LoginRequestDto login);

    User getUser(Long userIdx);

    boolean updateUser(Long userIdx, UpdateUserRequestDto userRequestDto);
}

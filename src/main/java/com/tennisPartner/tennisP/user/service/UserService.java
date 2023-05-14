package com.tennisPartner.tennisP.user.service;


import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.ReCreateTokenResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User join(JoinRequestDto join);
    LoginResponseDto login(LoginRequestDto login);

    GetUserResponseDto getUser(Long userIdx);

    boolean updateUser(Long userIdx, UpdateUserRequestDto userRequestDto, MultipartFile userPhoto) throws IOException;

    ReCreateTokenResponseDto reCreateToken(String refreshToken);

    String getUserPhotoPath(String encodePath);
}

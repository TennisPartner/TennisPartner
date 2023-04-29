package com.tennisPartner.tennisP.user.repository.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class UpdateUserRequestDto {

    private String userNickname;
    private String userGender;
    private double userNtrp;
}

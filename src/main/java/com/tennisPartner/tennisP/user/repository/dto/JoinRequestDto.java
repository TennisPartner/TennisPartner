package com.tennisPartner.tennisP.user.repository.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tennisPartner.tennisP.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class JoinRequestDto {

    @NotBlank(message = "ID를 입력하여 주시기 바랍니다")
    @Size(min=4, max=20, message = "ID는 4~20 자리를 맞춰주시기 바랍니다.")
    private String userId;

    @NotBlank(message = "패스워드를 입력하여 주시기 바랍니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8}", message = "비밀번호는 8자 이상 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String userPassword;

    @NotBlank(message = "이름을 입력하여 주시기 바랍니다.")
    @Pattern(regexp = "^([A-Za-z가-힣]{2,})+", message = "이름을 제대로 입력하여 주시기 바랍니다.")
    private String userName;

    @NotBlank(message = "닉네임을 입력하여 주시기 바랍니다.")
    @Pattern(regexp = "^([A-Za-z0-9가-힣]{2,})+", message = "닉네임을 제대로 입력하여 주시기 바랍니다.")
    private String userNickname;

    @NotBlank(message = "성별을을 선택하여 주기 바랍니다.")
    private String userGender;

    @NotBlank(message = "ntrp는 입력하여 주시기 바랍니다.")
    @Min(value = 0, message = "0~5 사이의 값을 입력하여 주시기 바랍니다.")
    @Max(value = 5, message = "0~5 사이의 값을 입력하여 주시기 바랍니다.")
    private double userNtrp;

    @NotBlank(message = "사진을 등록하여 주시기 바랍니다.")
    private String userPhotoPath;

    public User dtoToUserEntity(String encodePassword) {
        return User.builder()
                .userId(userId)
                .userPassword(encodePassword)
                .userName(userName)
                .userNickname(userNickname)
                .userGender(userGender)
                .userNtrp(userNtrp)
                .userPhotoPath(userPhotoPath)
                .build();
    }

}

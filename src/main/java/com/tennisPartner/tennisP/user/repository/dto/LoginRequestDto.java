package com.tennisPartner.tennisP.user.repository.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "입력 정보를 다시 확인하여 주시기 바랍니다.")
    private String userId;

    @NotBlank(message = "입력 정보를 다시 확인하여 주시기 바랍니다.")
    private String userPassword;
}

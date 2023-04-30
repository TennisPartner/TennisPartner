package com.tennisPartner.tennisP.user.repository.dto;

import com.tennisPartner.tennisP.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetUserResponseDto {

    private String userId;
    private String userName;
    private String userNickname;
    private String userGender;
    private double userNtrp;

    public GetUserResponseDto(User Entity){
        this.userId = Entity.getUserId();
        this.userName = Entity.getUserName();
        this.userNickname = Entity.getUserNickname();
        this.userGender = Entity.getUserGender();
        this.userNtrp = Entity.getUserNtrp();
    }

}

package com.tennisPartner.tennisP.user.repository.dto;

import com.tennisPartner.tennisP.common.util.ImageUtil;
import com.tennisPartner.tennisP.user.domain.User;
import io.netty.util.internal.StringUtil;
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

    private Long userIdx;
    private String userId;
    private String userName;
    private String userNickname;
    private String userGender;
    private String userPhotoPath;
    private double userNtrp;

    public GetUserResponseDto(User Entity){
        this.userIdx = Entity.getUserIdx();
        this.userId = Entity.getUserId();
        this.userName = Entity.getUserName();
        this.userNickname = Entity.getUserNickname();
        this.userGender = Entity.getUserGender();
        this.userPhotoPath = Entity.getUserPhotoPath();
        this.userNtrp = Entity.getUserNtrp();
    }

}

package com.tennisPartner.tennisP.user.domain;

import javax.persistence.Id;

public class RefreshToken {

    @Id
    private String refreshToken;
    private Long userIdx;

    public RefreshToken(String refreshToken, Long userIdx) {
        this.refreshToken = refreshToken;
        this.userIdx = userIdx;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getUserIdx() {
        return userIdx;
    }

}

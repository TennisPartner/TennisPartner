package com.tennisPartner.tennisP.AuthMatch.repository.selectColumn;

import com.tennisPartner.tennisP.user.domain.User;

public interface HostScore {
    Long getHost1();
    Long getHost2();
    int getHostScore();
    int getGuestScore();

}

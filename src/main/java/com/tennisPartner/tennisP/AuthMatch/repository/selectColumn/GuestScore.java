package com.tennisPartner.tennisP.AuthMatch.repository.selectColumn;

import com.tennisPartner.tennisP.user.domain.User;

public interface GuestScore {
    Long getGuest1();
    Long getGuest2();
    int getHostScore();
    int getGuestScore();

}

package com.tennisPartner.tennisP.user.service;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void save() {
        //given
        JoinRequestDto join = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");

        //when
        User user = join.dtoToUserEntity();
        System.out.println("user.getCreateDt() = " + user.getCreateDt());
        User joinedUser = userService.join(join);
        System.out.println("user = " + user.getUserId());
        System.out.println("user = " + joinedUser.getUserIdx());

        //then
        Assertions.assertThat(joinedUser.getUserId()).isEqualTo(user.getUserId());
        Assertions.assertThat(joinedUser.getUserIdx()).isEqualTo(1);


    }

}
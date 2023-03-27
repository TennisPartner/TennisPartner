package com.tennisPartner.tennisP.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void save() {
        //given
        JoinRequestDto join1 = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");
        JoinRequestDto join2 = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");

        //when
        User user = join1.dtoToUserEntity(passwordEncoder.encode(join1.getUserPassword()));
        User joinUser = userService.join(join1);

        //then
        assertThat(joinUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(joinUser.getUserIdx()).isEqualTo(1);
        assertThrows(BadCredentialsException.class, () -> {
            userService.join(join2);
        });
    }

    @Test
    void login() {
        //given
        JoinRequestDto join = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");
        LoginRequestDto loginSuccess = new LoginRequestDto("test", "123123");
        LoginRequestDto loginFail = new LoginRequestDto("test", "123");

        //when
        User joinUser = userService.join(join);
        LoginResponseDto loginUser = userService.login(loginSuccess);
        System.out.println("joinUser = " + joinUser.getUserPassword());
        //then
        assertThat(loginUser.getIdx()).isEqualTo(joinUser.getUserIdx());

    }

}
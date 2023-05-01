package com.tennisPartner.tennisP.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
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
        System.out.println("loginUser = " + loginUser.getAccessToken());
        //then
        assertThat(loginUser.getIdx()).isEqualTo(joinUser.getUserIdx());
        assertThat(userService.login(loginSuccess)).isInstanceOf(LoginResponseDto.class);

    }

    @Test
    void pathTest() throws FileNotFoundException {
        URL resource = getClass().getResource("/img/image1.jpg");
        System.out.println("resource = " + resource);

        FileInputStream fileInputStream = new FileInputStream(resource.getFile());
        System.out.println("fileInputStream = " + fileInputStream);

    }

    @Test
    void imageUploadTest() throws IOException {
        //given
        URL resource = getClass().getResource("/img/image2.jpg");
//        URL resource = getClass().getResource("/img/test.txt");

        MockMultipartFile multipartFile = new MockMultipartFile("image", "upload_test.jpg",
                "image/jpeg",
                new FileInputStream(resource.getFile()));
//        MockMultipartFile multipartFile = new MockMultipartFile("text", "test.txt",
//                "html/plain",
//                new FileInputStream(resource.getFile()));

        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto("test", "M", 4.3);

        //when
        boolean tf = userService.updateUser(1L, updateUserRequestDto, multipartFile);

        //then
        Assertions.assertThat(tf).isTrue();
    }

}
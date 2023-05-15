package com.tennisPartner.tennisP.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
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
    @Autowired
    JpaUserRepository repository;

    private Long userIdx;

    @BeforeEach
    void joinUser() {
        JoinRequestDto join1 = new JoinRequestDto("test", "123123", "test", "", "", 0, "");
        User joinUser = userService.join(join1);
        userIdx = joinUser.getUserIdx();
    }


    @Test
    void save() {
        //given
        JoinRequestDto join1 = new JoinRequestDto("test123", "123123", "test", "test", "F", 3.5, "test");
        JoinRequestDto join2 = new JoinRequestDto("test123", "123123", "test", "test", "F", 3.5, "test");

        //when
        User user = join1.dtoToUserEntity(passwordEncoder.encode(join1.getUserPassword()));
        User joinUser = userService.join(join1);

        //then
        assertThat(joinUser.getUserId()).isEqualTo(user.getUserId());
        assertThrows(CustomException.class, () -> {
            userService.join(join2);
        });
    }

    @Test
    void login() {
        //given
        LoginRequestDto loginSuccess = new LoginRequestDto("test", "123123");

        //when
        LoginResponseDto loginUser = userService.login(loginSuccess);

        //then
        assertThat(loginUser.getIdx()).isEqualTo(userIdx);
        assertThat(userService.login(loginSuccess)).isInstanceOf(LoginResponseDto.class);

    }

    @Test
    void pathTest() throws IOException {
        URL resource = getClass().getResource("/img/image1.jpg");
        System.out.println("resource = " + resource);

        FileInputStream fileInputStream = new FileInputStream(resource.getFile());
        System.out.println("fileInputStream = " + fileInputStream);

        MockMultipartFile multipartFile = new MockMultipartFile("image", "upload_test.jpg",
                "image/jpeg",
                new FileInputStream(resource.getFile()));


    }

    @Test
    void imageUploadTest() throws IOException {
        //given
        URL resource = getClass().getResource("/img/image2.jpg");

        User findUser = repository.findById(userIdx).get();

        MockMultipartFile multipartFile = new MockMultipartFile("image", "upload_test.jpg",
                "image/jpeg",
                new FileInputStream(resource.getFile()));

        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto("test", "M", 4.3);

        //when
        boolean tf = userService.updateUser(userIdx, updateUserRequestDto, multipartFile);
        User updateUser = repository.findById(userIdx).get();
        System.out.println("updateUser.getUserNtrp() = " + updateUser.getUserNtrp());

        //then
        assertThat(tf).isTrue();
        assertThat(updateUser.getUserId()).isEqualTo(findUser.getUserId());
        assertThat(updateUser.getUserNtrp()).isEqualTo(4.3);
    }

}
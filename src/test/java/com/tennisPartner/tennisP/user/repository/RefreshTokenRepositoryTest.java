package com.tennisPartner.tennisP.user.repository;

import com.tennisPartner.tennisP.user.UserGrade;
import com.tennisPartner.tennisP.user.domain.RefreshToken;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    @DisplayName("refresh Token 저장 확인")
    void saveTest() {
        //given
        JoinRequestDto join = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");
        System.out.println("join = " + join.getUserPassword());
        User joinUser = userService.join(join);
        LoginRequestDto loginRequest = new LoginRequestDto(join.getUserId(), join.getUserPassword());

        //when
        LoginResponseDto loginResponse = userService.login(loginRequest);
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(loginResponse.getRefreshToken()).get();

        //then
        Assertions.assertThat(refreshToken.getRefreshToken()).isEqualTo(loginResponse.getRefreshToken());
        Assertions.assertThat(refreshToken.getUserIdx()).isEqualTo(loginResponse.getIdx());
    }

    @Test
    void updateTest() throws InterruptedException {
        //given
        JoinRequestDto join = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");
        userService.join(join);
        LoginRequestDto loginRequest = new LoginRequestDto(join.getUserId(), join.getUserPassword());

        //when
        LoginResponseDto loginResponse = userService.login(loginRequest);
        String refreshToken = loginResponse.getRefreshToken();
        Long expire = redisTemplate.getExpire(refreshToken);
        Long findIdx = (Long) redisTemplate.opsForValue().get(refreshToken);

        RefreshToken updateRefreshToken = jwtProvider.updateRefreshToken(refreshToken);
        Long updateExpire = redisTemplate.getExpire(updateRefreshToken.getRefreshToken());
        Long updateIdx = (Long) redisTemplate.opsForValue().get(updateRefreshToken.getRefreshToken());

        Assertions.assertThat(updateExpire).isEqualTo(expire);
        Assertions.assertThat(updateIdx).isEqualTo(findIdx);

    }

}
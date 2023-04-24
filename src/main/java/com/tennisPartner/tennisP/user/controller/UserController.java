package com.tennisPartner.tennisP.user.controller;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import com.tennisPartner.tennisP.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    public ResponseEntity join(@RequestBody @Validated JoinRequestDto join, BindingResult result) {
        log.info("join: {}", join.getUserId());
        log.info("join: {}", join.getUserPassword());

        if (result.hasErrors()) {
            return new ResponseEntity(result.getFieldError().getDefaultMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        User joinUser = userService.join(join);

        return new ResponseEntity(joinUser.getUserIdx(), HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody @Validated LoginRequestDto login,
            BindingResult result) {
        log.info("login: {}", login.getUserId());

        LoginResponseDto loginUser = userService.login(login);

        return new ResponseEntity(loginUser, HttpStatus.OK);
    }

    @GetMapping("/login/api/users")
    public ResponseEntity getUser(
            @LoginMemberId Long userIdx
    ) {

        log.info("accessToken: {}", userIdx);
        GetUserResponseDto getUserResponseDto = userService.getUser(userIdx);

        if (getUserResponseDto == null) {
            return new ResponseEntity("유저 미존재", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(getUserResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/login/api/users")
    public ResponseEntity updateUser(@LoginMemberId Long userIdx,
            @RequestBody @Validated UpdateUserRequestDto updateUser,
            BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity(result.getFieldError().getDefaultMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        boolean updateYN = userService.updateUser(userIdx, updateUser);

        if (!updateYN) {
            return new ResponseEntity("유저 미존재", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}

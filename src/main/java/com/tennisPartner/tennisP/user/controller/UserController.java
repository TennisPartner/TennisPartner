package com.tennisPartner.tennisP.user.controller;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
            return new ResponseEntity(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        User joinUser = userService.join(join);

        return new ResponseEntity(joinUser.getUserIdx(), HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody @Validated LoginRequestDto login, BindingResult result) {
        log.info("login: {}", login.getUserId());

        LoginResponseDto loginUser = userService.login(login);

        return new ResponseEntity(loginUser, HttpStatus.OK);
    }
}

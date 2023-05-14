package com.tennisPartner.tennisP.user.controller;

import com.tennisPartner.tennisP.common.util.ImageUtil;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.ReCreateTokenResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import com.tennisPartner.tennisP.user.resolver.LoginMemberId;
import com.tennisPartner.tennisP.user.service.UserService;
import io.jsonwebtoken.JwtException;
import io.swagger.models.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    public ResponseEntity join(@RequestBody @Validated JoinRequestDto join, BindingResult result) {

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

        LoginResponseDto loginUser = userService.login(login);

        return new ResponseEntity(loginUser, HttpStatus.OK);
    }

    @GetMapping("/login/api/users")
    public ResponseEntity getUser(
            @LoginMemberId Long userIdx
    ) throws MalformedURLException {

        GetUserResponseDto getUserResponseDto = userService.getUser(userIdx);

        if (getUserResponseDto == null) {
            return new ResponseEntity("유저 미존재", HttpStatus.BAD_REQUEST);
        }

        log.info("urlResource: {}",
                new UrlResource("file:" + getUserResponseDto.getUserPhotoPath()));
        return new ResponseEntity(getUserResponseDto, HttpStatus.OK);
    }

    @PatchMapping(value = "/login/api/users",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity updateUser(@LoginMemberId Long userIdx,
            @RequestPart @Validated UpdateUserRequestDto updateUser,
            @RequestPart MultipartFile userPhoto,
            BindingResult result) throws IOException {

        if (result.hasErrors()) {
            return new ResponseEntity(result.getFieldError().getDefaultMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        boolean updateYN = userService.updateUser(userIdx, updateUser, userPhoto);

        if (!updateYN) {
            return new ResponseEntity("유저 미존재", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/gen")
    public ResponseEntity reCreateToken(
            @RequestHeader("RefreshAuthorization") String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            throw new JwtException("refreshToken 미존재");
        }
        ReCreateTokenResponseDto reCreateTokenResponseDto = userService.reCreateToken(refreshToken);
        return new ResponseEntity(reCreateTokenResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/api/users/{encodePath}")
    public UrlResource userPhoto(@PathVariable String encodePath) throws MalformedURLException {
        String decodeUserPhotoPath = userService.getUserPhotoPath(encodePath);
        log.info(String.valueOf(new UrlResource("file:" + decodeUserPhotoPath)));
        return new UrlResource("file:" + decodeUserPhotoPath);
    }
}

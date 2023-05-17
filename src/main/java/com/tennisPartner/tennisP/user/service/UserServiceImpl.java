package com.tennisPartner.tennisP.user.service;

import com.tennisPartner.tennisP.common.Exception.CustomException;
import com.tennisPartner.tennisP.common.util.ImageUtil;
import com.tennisPartner.tennisP.user.domain.RefreshToken;
import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.RefreshTokenRepository;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.ReCreateTokenResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final JpaUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository tokenRepository;
    @Value("${upload.path.user}")
    private String UPLOAD_PATH;

    @Override
    public User join(JoinRequestDto join) {
        String userId = join.getUserId();

        if (!repository.findByUserIdAndUseYn(userId, "Y").isPresent()) {
            User joinUser = join.dtoToUserEntity(passwordEncoder.encode(join.getUserPassword()));
            return repository.save(joinUser);
        } else {
            throw new CustomException("아이디 중복", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto login) {

        User loginUser = repository.findByUserIdAndUseYn(login.getUserId(), "Y").orElseThrow(
                () -> new CustomException("아이디랑 비밀번호를 다시 확인하여 주시기 바랍니다.", HttpServletResponse.SC_BAD_REQUEST)
        );
        if (!passwordEncoder.matches(login.getUserPassword(), loginUser.getUserPassword())) {
            throw new CustomException("아이디랑 비밀번호를 다시 확인하여 주시기 바랍니다.", HttpServletResponse.SC_BAD_REQUEST);
        }
        return LoginResponseDto.builder()
                .idx(loginUser.getUserIdx())
                .refreshToken(jwtProvider.createRefreshToken(loginUser.getUserIdx()))
                .accessToken(jwtProvider.createAccessToken(loginUser.getUserIdx()))
                .build();
    }

    @Override
    public GetUserResponseDto getUser(Long userIdx) {
        Optional<User> findUser = repository.findByUserIdxAndUseYn(userIdx, "Y");

        if (!findUser.isEmpty()) {
            GetUserResponseDto getUserResponseDto = findUser.stream()
                    .map(u -> new GetUserResponseDto(u))
                    .findFirst()
                    .get();
            return getUserResponseDto;
        }
        return null;
    }

    @Override
    public boolean updateUser(Long userIdx, UpdateUserRequestDto userRequestDto, MultipartFile userPhoto)
            throws IOException {
        Optional<User> findUser = repository.findById(userIdx);

        if (!findUser.isEmpty()) {
            User updateUser = findUser.get();
            if (updateUser.getUseYn().equals("Y")) {
                String savePath;
                if (userPhoto != null) {
                    savePath = ImageUtil.imageSave(UPLOAD_PATH, updateUser.getUserIdx(), userPhoto);
                } else {
                    savePath = updateUser.getUserPhotoPath();
                }
                updateUser.updateUser(userRequestDto, savePath);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public ReCreateTokenResponseDto reCreateToken(String refreshToken) {
        RefreshToken validRefreshToken = jwtProvider.validateRefreshToken(refreshToken);
        String newAccessToken = jwtProvider.createAccessToken(validRefreshToken.getUserIdx());
        RefreshToken newRefreshToken = jwtProvider.updateRefreshToken(refreshToken);

        if (newRefreshToken == null) {
            throw new CustomException("refreshToken null", HttpServletResponse.SC_BAD_REQUEST);
        }

        return new ReCreateTokenResponseDto(
                newRefreshToken, newAccessToken);
    }

    @Override
    public String getUserPhotoPath(String encodePath) {
        String decodePath = ImageUtil.getDecodeUserPhotoPath(encodePath);
        String decodeUserPhotoPath = Paths.get(UPLOAD_PATH, decodePath).toString();
        return decodeUserPhotoPath;
    }

}

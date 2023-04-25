package com.tennisPartner.tennisP.user.service;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.GetUserResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginRequestDto;
import com.tennisPartner.tennisP.user.repository.dto.LoginResponseDto;
import com.tennisPartner.tennisP.user.repository.dto.UpdateUserRequestDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final JpaUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public User join(JoinRequestDto join) {
        String userId = join.getUserId();

        if (!repository.findByUserId(userId).isPresent()) {
            User joinUser = join.dtoToUserEntity(passwordEncoder.encode(join.getUserPassword()));
            return repository.save(joinUser);
        } else {
            throw new BadCredentialsException("아이디 중복");
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto login) {

        User loginUser = repository.findByUserId(login.getUserId()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정입니다.")
        );

        if (!passwordEncoder.matches(login.getUserPassword(), loginUser.getUserPassword())) {
            throw new BadCredentialsException("비밀번호 오류입니다.");
        }

        return LoginResponseDto.builder()
                .idx(loginUser.getUserIdx())
                .refreshToken(jwtProvider.createRefreshToken(loginUser.getUserIdx()))
                .accessToken(jwtProvider.createAccessToken(loginUser.getUserIdx()))
                .build();
    }

    @Override
    public GetUserResponseDto getUser(Long userIdx) {
        Optional<User> findUser = repository.findById(userIdx);

        if (!findUser.isEmpty()) {
            User user = findUser.get();
            GetUserResponseDto getUserResponseDto = new GetUserResponseDto(
                    user.getUserId(),
                    user.getUserName(),
                    user.getUserNickname(),
                    user.getUserGender(),
                    user.getUserNtrp()
            );
            return getUserResponseDto;
        }
        return null;
    }

    @Override
    public boolean updateUser(Long userIdx, UpdateUserRequestDto userRequestDto) {
        Optional<User> findUser = repository.findById(userIdx);

        if (!findUser.isEmpty()) {
            User updateUser = findUser.get();
            updateUser.updateUser(userRequestDto);
            return true;
        }
        return false;
    }

}

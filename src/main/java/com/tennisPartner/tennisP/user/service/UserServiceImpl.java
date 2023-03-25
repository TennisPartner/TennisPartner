package com.tennisPartner.tennisP.user.service;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
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
}

package com.tennisPartner.tennisP.user.service;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final JpaUserRepository repository;

    @Override
    public User join(JoinRequestDto join) {

        User joinUser = join.dtoToUserEntity();

        return repository.save(joinUser);
    }
}

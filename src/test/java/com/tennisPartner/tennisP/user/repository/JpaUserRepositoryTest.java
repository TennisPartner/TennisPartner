package com.tennisPartner.tennisP.user.repository;


import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.dto.JoinRequestDto;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired
    JpaUserRepository jpaUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findByUserId() {
        //given
        JoinRequestDto join1 = new JoinRequestDto("test", "123123", "test", "test", "F", 3.5, "test");

        //when
        User user = join1.dtoToUserEntity(passwordEncoder.encode(join1.getUserPassword()));
        User joinUser = jpaUserRepository.save(user);
        Optional<User> findUser = jpaUserRepository.findByUserId("test");

        //then
        Assertions.assertThat(findUser.get()).usingRecursiveComparison().isEqualTo(joinUser);

    }
}
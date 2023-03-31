package com.tennisPartner.tennisP.user.jwt;

import com.tennisPartner.tennisP.user.domain.User;
import com.tennisPartner.tennisP.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userIdx) throws UsernameNotFoundException {

        User findUser = repository.findById(Long.parseLong(userIdx)).orElseThrow(
                () -> new UsernameNotFoundException("UserDetailService Error")
        );

        return new CustomUserDetails(findUser);
    }
}

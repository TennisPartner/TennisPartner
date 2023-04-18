package com.tennisPartner.tennisP.user.repository;

import com.tennisPartner.tennisP.user.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate redisTemplate;

    public void save(RefreshToken refreshToken) {
        log.info("refreshToken: {}, idx: {}, redisTemplate.opsForValue(): {}", refreshToken.getRefreshToken(), refreshToken.getUserIdx());
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getUserIdx());
        redisTemplate.expire(refreshToken.getRefreshToken(), 120L, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long userIdx = valueOperations.get(refreshToken);

        if (Objects.isNull(userIdx)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, userIdx));

    }

}
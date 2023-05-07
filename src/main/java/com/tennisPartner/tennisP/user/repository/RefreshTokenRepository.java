package com.tennisPartner.tennisP.user.repository;

import com.tennisPartner.tennisP.user.domain.RefreshToken;
import java.util.UUID;
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

    private final long exp = 14L;

    public void save(RefreshToken refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getUserIdx());
        redisTemplate.expire(refreshToken.getRefreshToken(), exp, TimeUnit.DAYS);
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long userIdx = valueOperations.get(refreshToken);

        if (Objects.isNull(userIdx)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, userIdx));

    }

    public RefreshToken updateByRefreshToken(String refreshToken) {
        String newRefreshToken = UUID.randomUUID().toString();
        redisTemplate.rename(refreshToken, newRefreshToken);
        Optional<RefreshToken> findRefreshToken = findByRefreshToken(newRefreshToken);
        return findRefreshToken.orElse(null);
    }

}

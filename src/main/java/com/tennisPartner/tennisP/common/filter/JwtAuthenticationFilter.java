package com.tennisPartner.tennisP.common.filter;

import com.tennisPartner.tennisP.user.domain.RefreshToken;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //header 에서 토큰 추출
        String accessToken = jwtProvider.resolveAccessToken(request);
        String refreshToken = jwtProvider.resolveRefreshToken(request);

        //refreshToken 유효성 검사
        if (StringUtils.hasText(refreshToken) && jwtProvider.validateRefreshToken(refreshToken) != null) {
            //유효성 통과 후 accessToken 및 refreshToken 재발급
            log.info("refreshToken: {}", refreshToken);
            Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
            refreshTokenRepository.deleteByRefreshToken(refreshToken);

            accessToken = "BEARER " + jwtProvider.createAccessToken(findRefreshToken.get().getUserIdx());
            refreshToken = jwtProvider.createRefreshToken(findRefreshToken.get().getUserIdx());
            log.info("newRefreshToken: {}", refreshToken);

            response.setHeader("Authorization", accessToken);
            response.setHeader("RefreshAuthorization", refreshToken);
        }
        //accessToken 유효성 검사
        if (StringUtils.hasText(accessToken) && jwtProvider.validateAccessToken(accessToken) != null) {
            // check access token
            accessToken = accessToken.split(" ")[1].trim();

            Authentication auth = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}

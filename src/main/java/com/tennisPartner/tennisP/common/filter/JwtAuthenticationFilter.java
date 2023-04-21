package com.tennisPartner.tennisP.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennisPartner.tennisP.user.domain.RefreshToken;
import com.tennisPartner.tennisP.user.jwt.JwtProvider;
import com.tennisPartner.tennisP.user.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        // ex.getMessage() 에는 jwtException을 발생시키면서 입력한 메세지가 들어있다.
        body.put("message", ex.getMessage());
        body.put("path", request.getServletPath());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

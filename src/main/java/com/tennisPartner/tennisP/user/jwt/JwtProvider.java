package com.tennisPartner.tennisP.user.jwt;

import com.tennisPartner.tennisP.user.domain.RefreshToken;
import com.tennisPartner.tennisP.user.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;
    //만료시간: 30s
//    private final long exp = 1000L * 60 * 5;
    private final long exp = 1000L * 30 ;

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createRefreshToken(Long userIdx) {

        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userIdx);
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getRefreshToken();
    }

    public RefreshToken updateRefreshToken(String oldRefreshToken) {
        return refreshTokenRepository.updateByRefreshToken(oldRefreshToken);
    }

    //토큰 생성
    public String createAccessToken(Long userIdx) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userIdx));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //권한정보 획득
    //Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdx(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //accessToken 남은 유효시간
    public Long getAccessExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody().getExpiration();
        Long now = new Date().getTime();

        return expiration.getTime() - now;
    }

    public String getUserIdx(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    //Authorization Header를 통해 인증
    public String resolveAccessToken(HttpServletRequest request) {
//        log.info("request Header: {}", request.getHeader("Authorization"));
        return request.getHeader("Authorization");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
//        log.info("request Header: {}", request.getHeader("RefreshAuthorization"));
        return request.getHeader("RefreshAuthorization");
    }

    //토큰 검증
    public boolean validateAccessToken(String accessToken) {
        try {
            accessToken = accessToken.split(" ")[1].trim();

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(accessToken);

            //만료되었을 시, false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            throw new JwtException("잘못된 JWT 시그니처");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new JwtException("유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new JwtException("토큰 기한 만료. Refresh Token 요청");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
    }
    //토큰 검증
//    public Claims validateAccessToken(String accessToken) {
//        try {
//            //Bearer검증
////            if (!accessToken.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
////                return false;
////            } else {
//            accessToken = accessToken.split(" ")[1].trim();
////            }
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken);
//
//            return claims.getBody();
//            //만료되었을 시, false
//            return !claims.getBody().getExpiration().before(new Date());
//
//        } catch (SecurityException e) {
//            log.info("Invalid JWT signature.");
//            throw new JwtException("잘못된 JWT 시그니처");
//        } catch (MalformedJwtException e) {
//            log.info("Invalid JWT token.");
//            throw new JwtException("유효하지 않은 JWT 토큰");
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT token.");
//            throw new JwtException("토큰 기한 만료. Refresh Token 요청");
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT token.");
//        } catch (IllegalArgumentException e) {
//            log.info("JWT token compact of handler are invalid.");
//            throw new JwtException("JWT token compact of handler are invalid.");
//        }
//        return null;
//    }

    public RefreshToken validateRefreshToken(String refreshToken) {
        try {
            Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
            if (findRefreshToken.isPresent()) {
                return findRefreshToken.get();
            }
        } catch (MalformedJwtException e) {
            log.info("Invalid Refresh token.");
            throw new JwtException("유효하지 않은 Refresh 토큰");
        } catch (ExpiredJwtException e) {
            log.info("Expired Refresh token.");
            throw new JwtException("토큰 기한 만료.");
        }
        return null;
    }

}

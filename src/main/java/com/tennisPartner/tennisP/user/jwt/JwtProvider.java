package com.tennisPartner.tennisP.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    //만료시간: 5m
    private final long exp = 1000L * 60 * 5;

    private final UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    //토큰 생성
    public String createToken(String userIdx) {
        Claims claims = Jwts.claims().setSubject(userIdx);
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //accessToken 남은 유효시간
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody().getExpiration();
        Long now = new Date().getTime();

        return expiration.getTime() - now;
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    //Authorization Header를 통해 인증
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    //토큰 검증
    public boolean validateToken(String token) {
        try {
            //Bearer검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            //만료되었을 시, false
            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }
    }

}

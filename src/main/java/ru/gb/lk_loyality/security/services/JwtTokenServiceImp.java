package ru.gb.lk_loyality.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.security.UserInfo;
import ru.gb.lk_loyality.security.repositories.JwtTokenService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


@Service
public class JwtTokenServiceImp implements JwtTokenService {
    @Value("${jwt.secret}")
    private String JWT_SECRET;


    @Override
    public String generateToken(UserInfo user) {
        Instant exp = Instant.now().plus(30, ChronoUnit.MINUTES);
        Date expDate = Date.from(exp);
        String cmpTokenString = Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getUserName())
                .claim("roles", user.getRoles())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return "Bearer " + cmpTokenString;
    }

    @Override
    public UserInfo parseToken(String token) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);

        String userName = jwsClaims
                .getBody().getSubject();

        Long id = jwsClaims
                .getBody().get("id", Long.class);

        List<String> roles = jwsClaims
                .getBody().get("roles", List.class);

        return UserInfo.builder()
                .id(id)
                .userName(userName)
                .roles(roles)
                .build();
    }
}

package ru.gb.lk_loyality.security.filters;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.gb.lk_loyality.security.UserInfo;
import ru.gb.lk_loyality.security.repositories.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService tokenService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaderIsInvalid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = createToken(authHeader);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken createToken(String authHeader) throws ExpiredJwtException {
        String token = authHeader.replace("Bearer ", "");
        UserInfo userInfo = tokenService.parseToken(token);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()) {
            userInfo.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
        }
        return new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
    }

    private boolean authHeaderIsInvalid(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }
}

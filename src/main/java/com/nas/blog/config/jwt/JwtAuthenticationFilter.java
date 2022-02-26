package com.nas.blog.config.jwt;

import com.nas.blog.config.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티 필터를 OncePerRequestFilter JwtFilter로 커스터마이징
// JWT 토큰 검사를 한 Request당 한번만 검사하고 싶기때문에 OncePerRequestFilter를 상속
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final PrincipalDetailService principalDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = resolveToken(request);
        System.out.println("accessToken = " + accessToken);

        if (accessToken != null && jwtTokenUtil.validateToken(accessToken)) {
            setAuthentication(accessToken);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtHeaderUtilEnums.AUTHORIZATION_HEADER.getValue());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtHeaderUtilEnums.GRANT_TYPE.getValue())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void checkLogout(String accessToken) {
    }

    private void setAuthentication(String accessToken) {
        String email = jwtTokenUtil.getEmail(accessToken);
        System.out.println("email = " + email);
        UserDetails userDetails = principalDetailService.loadUserByUsername(email);
        System.out.println("userDetails = " + userDetails);
        SecurityContextHolder.getContext().setAuthentication(jwtTokenUtil.getAuthentication(userDetails));
    }
}

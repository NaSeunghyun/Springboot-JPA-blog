package com.nas.blog.auth.controller;

import com.nas.blog.config.jwt.JwtHeaderUtilEnums;
import com.nas.blog.config.jwt.JwtTokenUtil;
import com.nas.blog.dto.ResponseDto;
import com.nas.blog.dto.TokenDto;
import com.nas.blog.user.controller.form.JoinForm;
import com.nas.blog.entity.User;
import com.nas.blog.user.controller.form.LoginForm;
import com.nas.blog.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/join")
    public ResponseDto save(@Validated @RequestBody JoinForm joinForm) {

        User user = getUser(joinForm);
        userService.Join(user);
        return ResponseDto.ok();
    }

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(@Validated @RequestBody LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        TokenDto token = userService.login(loginForm);
        Cookie accessToken = jwtTokenUtil.createAccessTokenCookie(token.getAccessToken());
        Cookie refreshToken = jwtTokenUtil.createRefreshTokenCookie(token.getRefreshToken());
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        return ResponseDto.ok(token);
    }

    private User getUser(JoinForm joinForm) {
        User user = User.builder()
                .userName(joinForm.getUsername())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .email(joinForm.getEmail())
                .role(joinForm.getRole())
                .build();
        return user;
    }

    @PostMapping("/logout")
    public ResponseDto logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie accessTokenCookie = jwtTokenUtil.getCookie(request, JwtHeaderUtilEnums.ACCESS_TOKEN_NAME.getValue());
        String accessToken = accessTokenCookie.getValue();

        if (accessToken != null) {
            userService.logout(accessToken);
        }

        SecurityContextHolder.clearContext();
        accessTokenCookie = jwtTokenUtil.removeCookie(JwtHeaderUtilEnums.ACCESS_TOKEN_NAME.getValue());
        Cookie refreshTokenCookie = jwtTokenUtil.removeCookie(JwtHeaderUtilEnums.REFRESH_TOKEN_NAME.getValue());

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseDto.ok();
    }

}

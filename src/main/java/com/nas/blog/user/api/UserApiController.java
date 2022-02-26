package com.nas.blog.user.api;

import com.nas.blog.config.jwt.JwtHeaderUtilEnums;
import com.nas.blog.config.jwt.JwtTokenUtil;
import com.nas.blog.dto.ResponseDto;
import com.nas.blog.dto.TokenDto;
import com.nas.blog.user.controller.form.JoinForm;
import com.nas.blog.entity.User;
import com.nas.blog.user.controller.form.LoginForm;
import com.nas.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/join")
    public ResponseDto save(@Validated @RequestBody JoinForm joinForm) {

        User user = getUser(joinForm);
        userService.Join(user);
        return ResponseDto.ok();
    }

    @PostMapping("/auth/login")
    public ResponseDto<TokenDto> login(HttpServletRequest request, @Validated @RequestBody LoginForm loginForm) {
        String referer = request.getHeader("referer");
        System.out.println("referer = " + referer);
        String email = userService.login(loginForm);
        Cookie refreshToken = jwtTokenUtil.createCookie(JwtHeaderUtilEnums.REFRESH_TOKEN_NAME.getValue(), email);
        String accessToken = jwtTokenUtil.generateAccessToken(email);
        return ResponseDto.ok(TokenDto.of(accessToken));
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
}

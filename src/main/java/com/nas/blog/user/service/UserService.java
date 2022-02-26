package com.nas.blog.user.service;

import com.nas.blog.config.auth.PrincipalDetail;
import com.nas.blog.config.jwt.JwtTokenUtil;
import com.nas.blog.dto.TokenDto;
import com.nas.blog.entity.User;
import com.nas.blog.exception.FieldException;
import com.nas.blog.user.controller.form.LoginForm;
import com.nas.blog.user.repository.UserRepository;
import com.nas.blog.util.constant.FieldConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nas.blog.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenUtil jwtTokenUtil;

    public void Join(User user) {
        existUserEmail(user);
        userRepository.save(user);
    }

    public String login(LoginForm loginForm) {
        // Login EMAIL/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginForm.toAuthentication();

        // 검증
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

        return principalDetail.getUser().getEmail();

    }

    private void existUserEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new FieldException(DUPLICATE_EMAIL, FieldConstant.EMAIL);
        }
    }
}

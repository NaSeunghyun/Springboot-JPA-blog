package com.nas.blog.user.api;

import com.nas.blog.user.controller.form.JoinForm;
import com.nas.blog.entity.User;
import com.nas.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/join")
    public HttpEntity save(@Validated @RequestBody JoinForm joinForm){

        User user = getUser(joinForm);
        userService.Join(user);
        return ResponseEntity.status(HttpStatus.OK).body("1");
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

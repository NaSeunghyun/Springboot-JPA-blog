package com.nas.blog.api;

import com.nas.blog.controller.form.JoinForm;
import com.nas.blog.dto.ResponseDto;
import com.nas.blog.entity.User;
import com.nas.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody JoinForm joinForm){
        User user = getUser(joinForm);
        userService.Join(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    private User getUser(JoinForm joinForm) {
        User user = User.builder()
                .userName(joinForm.getUserName())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .email(joinForm.getEmail())
                .role(joinForm.getRole())
                .build();
        return user;
    }
}

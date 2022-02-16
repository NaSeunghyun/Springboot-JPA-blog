package com.nas.blog.user.service;

import com.nas.blog.entity.User;
import com.nas.blog.exception.FieldException;
import com.nas.blog.user.repository.UserRepository;
import com.nas.blog.util.constant.FieldConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nas.blog.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void Join(User user) {
        existUserEmail(user);
        userRepository.save(user);
    }

    private void existUserEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new FieldException(DUPLICATE_EMAIL, FieldConstant.EMAIL);
        }
    }
}

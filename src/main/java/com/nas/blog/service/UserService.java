package com.nas.blog.service;

import com.nas.blog.entity.User;
import com.nas.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void Join(User user) {
        userRepository.save(user);
    }
}

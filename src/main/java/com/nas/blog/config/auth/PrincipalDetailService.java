package com.nas.blog.config.auth;

import com.nas.blog.entity.User;
import com.nas.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User principal = getPrincipal(email);
        return new PrincipalDetail(principal);
    }

    private User getPrincipal(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("로그인에 실패하였습니다.");
        });
    }
}

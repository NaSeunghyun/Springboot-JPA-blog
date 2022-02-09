package com.nas.blog.repository;

import com.nas.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(String Email);
}

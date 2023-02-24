package com.springboot.blog.springbootblog.Repository;

import com.springboot.blog.springbootblog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsernameOrEmail(String email, String Username);
    Optional<User> findUserByUsername(String Username);
    Boolean existsByUsername(String Username);
    Boolean existsByEmail(String email);
}

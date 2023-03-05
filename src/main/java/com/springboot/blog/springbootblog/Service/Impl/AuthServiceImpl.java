package com.springboot.blog.springbootblog.Service.Impl;

import com.springboot.blog.springbootblog.Entity.Role;
import com.springboot.blog.springbootblog.Entity.User;
import com.springboot.blog.springbootblog.Exceptions.BlogAPIException;
import com.springboot.blog.springbootblog.Payload.LoginDto;
import com.springboot.blog.springbootblog.Payload.RegisterDto;
import com.springboot.blog.springbootblog.Repository.RoleRepository;
import com.springboot.blog.springbootblog.Repository.UserRepository;
import com.springboot.blog.springbootblog.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged-in Successfully";
    }

    @Override
    public String signup(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already in use");

        if(userRepository.existsByUsername(registerDto.getUsername()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already in use");

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findRoleByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public String who() {
        User user = GetUser();
        if(user == null) return "Hello Anonymous";
        return "Hello "+user.getUsername();
    }

    private User GetUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return userRepository.findUserByUsernameOrEmail(auth.getName(), auth.getName()).orElse(null);
    }

}

package com.springboot.blog.springbootblog.Service;

import com.springboot.blog.springbootblog.Payload.LoginDto;
import com.springboot.blog.springbootblog.Payload.RegisterDto;
import org.springframework.stereotype.Service;

public interface AuthService {
    String login(LoginDto loginDto);
    String signup(RegisterDto registerDto);
}

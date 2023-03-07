package com.springboot.blog.springbootblog.Controller;

import com.springboot.blog.springbootblog.Entity.JwtAuthResponse;
import com.springboot.blog.springbootblog.Payload.LoginDto;
import com.springboot.blog.springbootblog.Payload.RegisterDto;
import com.springboot.blog.springbootblog.Security.JwtTokenProvider;
import com.springboot.blog.springbootblog.Service.AuthService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token  = authService.login(loginDto);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/signup", "/register"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.signup(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/who", "/me"})
    public ResponseEntity<String> who(HttpServletRequest request) {
        return new ResponseEntity<>(authService.who(), HttpStatus.OK);
    }
}

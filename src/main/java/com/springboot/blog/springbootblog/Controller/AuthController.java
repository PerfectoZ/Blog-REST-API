package com.springboot.blog.springbootblog.Controller;

import com.springboot.blog.springbootblog.Payload.LoginDto;
import com.springboot.blog.springbootblog.Payload.RegisterDto;
import com.springboot.blog.springbootblog.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/signup", "/register"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.signup(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/who", "/me"})
    public ResponseEntity<String> who() {
        return new ResponseEntity<>(authService.who(), HttpStatus.OK);
    }
}

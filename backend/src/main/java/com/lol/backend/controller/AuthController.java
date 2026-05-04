package com.lol.backend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.backend.dto.LoginRequestDto;
import com.lol.backend.dto.RegisterRequestDto;
import com.lol.backend.entity.User;
import com.lol.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequestDto request) {
        return authService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword());
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto request) {

        return authService.login(
                request.getEmail(),
                request.getPassword());
    }

    @GetMapping("/me")
    public User me() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return authService.getUserByEmail(email);
    }
}

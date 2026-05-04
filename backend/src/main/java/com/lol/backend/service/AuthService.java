package com.lol.backend.service;

import org.springframework.stereotype.Service;

import com.lol.backend.entity.User;
import com.lol.backend.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public String login(String email, String password) {
        User user = userService.authenticate(email, password);

        return jwtService.generateToken(user.getEmail());
    }

    public User register(String username, String email, String password) {
        return userService.register(username, email, password);
    }

    public User getUserByEmail(String email) {
        return userService.findByEmail(email);
    }
}
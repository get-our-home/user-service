package com.getourhome.userservice.controller;

import com.getourhome.userservice.dto.request.LoginRequestDto;
import com.getourhome.userservice.dto.request.UserRegisterDto;
import com.getourhome.userservice.entity.User;
import com.getourhome.userservice.service.AuthService;
import com.getourhome.userservice.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        if (authService.findByUserId(userRegisterDto.getUserId()).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Username already exists");
            return ResponseEntity.badRequest().body(response);
        }
        if (authService.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }
        authService.registerUser(userRegisterDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        User user = authService.login(loginRequestDto);
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid username or password");
            return ResponseEntity.badRequest().body(response);
        }

        // Normally you would return a JWT or a session token here
        // return ResponseEntity.ok(responseDto);
        return ResponseEntity.ok(jwtTokenProvider.createToken(user.getId(), user.getUserId()));
    }
}

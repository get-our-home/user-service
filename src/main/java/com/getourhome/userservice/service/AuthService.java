package com.getourhome.userservice.service;

import com.getourhome.userservice.dto.request.LoginRequestDto;
import com.getourhome.userservice.dto.request.UserRegisterDto;
import com.getourhome.userservice.entity.User;
import com.getourhome.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = userRegisterDto.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    public User login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUserId(loginRequestDto.getUserId()).orElse(null);
        if (user == null || !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }
}

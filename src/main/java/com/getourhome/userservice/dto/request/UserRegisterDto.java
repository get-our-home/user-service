package com.getourhome.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.getourhome.userservice.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class UserRegisterDto {
    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private String userId;
    @NotNull(message = "username cannot be null")
    private String username;
    @NotNull(message = "phoneNumber cannot be null")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotNull(message = "Password cannot be null")
    private String password;
    @NotNull(message = "Email cannot be null")
    private String email;

    @Builder
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .username(username)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}

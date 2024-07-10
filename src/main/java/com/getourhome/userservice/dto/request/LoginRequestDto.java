package com.getourhome.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private String userId;
    @NotNull(message = "Password cannot be null")
    private String password;
}

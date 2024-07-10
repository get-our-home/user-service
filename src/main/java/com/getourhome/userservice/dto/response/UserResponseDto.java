package com.getourhome.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.getourhome.userservice.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponseDto {
    private UUID id;
    @JsonProperty("user_id")
    private String userId;

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .build();
    }
}
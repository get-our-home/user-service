package com.getourhome.userservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseResponseDto {
    private String message;
}

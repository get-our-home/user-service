package com.getourhome.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.getourhome.userservice.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder(builderMethodName = "newBuilder")
@Schema(title = "회원가입 요청 DTO")
public class UserRegisterDto {
    @NotBlank(message = "사용자 아이디를 입력해주세요")
    @JsonProperty("user_id")
    @Schema(description = "사용자 아이디", example = "testerkim")
    private String userId;
    @NotBlank(message = "사용자 이름을 입력해주세요")
    @Schema(description = "사용자 이름", example = "김테스트")
    private String username;
    @NotBlank(message = "사용자 전화번호를 입력해주세요")
    @JsonProperty("phone_number")
    @Schema(description = "사용자 전화번호", example = "01012341234")
    private String phoneNumber;
    @NotBlank(message = "사용자 비밀번호를 입력해주세요")
    @Schema(description = "사용자 비밀번호", example = "tester123")
    private String password;
    @NotBlank(message = "사용자 이메일을 입력해주세요")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Schema(description = "사용자 이메일", example = "tester@tests.com")
    private String email;

    @Schema(description = "가입 경로(지인, 광고, 검색, 기타)", example = "지인")
    private String signUpPath;

    @Schema(description = "매물 선호 타입", example = "월세")
    private String preferredPropertyType;

    @Builder
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .username(username)
                .phoneNumber(phoneNumber)
                .email(email)
                .signUpPath(signUpPath)
                .preferredPropertyType(preferredPropertyType)
                .build();
    }
}

package com.getourhome.userservice.controller;

import com.getourhome.userservice.dto.request.LoginRequestDto;
import com.getourhome.userservice.dto.request.UserRegisterDto;
import com.getourhome.userservice.dto.response.BaseResponseDto;
import com.getourhome.userservice.dto.response.UserResponseDto;
import com.getourhome.userservice.entity.User;
import com.getourhome.userservice.service.AuthService;
import com.getourhome.userservice.util.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth API", description = "회원가입, 로그인에 대한 API입니다.")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "유저 이름, 유저 아이디, 비밀번호, 전화번호를 받고 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "사용자 ID 중복",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "사용자 이메일 중복",
                    content = @Content)})
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        if (authService.findByUserId(userRegisterDto.getUserId()).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "이미 존재하는 유저이이디 입니다.");
            return ResponseEntity.badRequest().body(response);
        }
        if (authService.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "이미 존재하는 이메일입니다.");
            return ResponseEntity.badRequest().body(response);
        }
        authService.registerUser(userRegisterDto);
        String msg = "회원가입 성공";
        BaseResponseDto baseResponseDto = BaseResponseDto.builder().message(msg).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "유저 아이디, 비밀번호로 로그인을 진행합니다. 로그인이 성공하면 JWT토큰을 전달합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "유저 ID 또는 비밀번호를 찾을 수 없음",
                    content = @Content)})
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        User user = authService.login(loginRequestDto);
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "유효하지 않은 이메일, 비밀번호입니다.\n다시 확인해주세요.");
            return ResponseEntity.badRequest().body(response);
        }

        UserResponseDto userResponseDto = UserResponseDto
                .builder()
                .role("USER")
                .jwt(jwtTokenProvider.createTokenWithoutExpiration(user.getId(), user.getUserId()))
                .build();
        return ResponseEntity.ok(userResponseDto);
    }
}

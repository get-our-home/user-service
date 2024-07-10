package com.getourhome.userservice.service;

import com.getourhome.userservice.dto.request.LoginRequestDto;
import com.getourhome.userservice.dto.request.UserRegisterDto;
import com.getourhome.userservice.entity.User;
import com.getourhome.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(properties = {
        "security.jwt.token.secret-key=mySecretKeymySecretKeymySecretKeymySecretKeymySecretKey",
        "security.jwt.token.expire-length=3600000"
})
@AutoConfigureMockMvc
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // 테스트 실행 전 데이터베이스 정리
    }

    @DisplayName("사용자 회원가입 성공시 DB 정상 반영 테스트")
    @Test
    void givenUserRegisterDto_whenRegisterUser_thenUserIsSaved() {
        // Given
        UserRegisterDto userRegisterDto = UserRegisterDto
                .newBuilder()
                .userId("tester")
                .username("김테스트")
                .phoneNumber("01012341234")
                .password("tester1234")
                .email("tester@test.com")
                .build();

        // When
        authService.registerUser(userRegisterDto);

        // Then
        Optional<User> userOptional = userRepository.findByUserId("tester");


        assertThat(userOptional).isPresent();
        User user = userOptional.get();
        assertThat(user.getUserId()).isEqualTo("tester");
        assertThat(user.getUsername()).isEqualTo("김테스트");
        assertThat(user.getPhoneNumber()).isEqualTo("01012341234");
        assertThat(passwordEncoder.matches("tester1234", user.getPassword())).isTrue();
        assertThat(user.getEmail()).isEqualTo("tester@test.com");
    }

    @DisplayName("사용자 로그인 정상 응답 테스트")
    @Test
    void givenValidCredentials_whenLoginUser_thenReturnUser() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .userId("tester")
                .username("김테스트")
                .phoneNumber("01012341234")
                .password(passwordEncoder.encode("tester1234"))
                .email("tester@test.com")
                .build();
        userRepository.save(user);

        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .userId("tester")
                .password("tester1234")
                .build();

        // When
        User loggedInUser = authService.login(loginRequestDto);

        // Then
        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getUserId()).isEqualTo("tester");
    }

    @Test
    void givenWrongPassword_whenLoginUser_thenReturnNull() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .userId("tester")
                .username("김테스트")
                .phoneNumber("01012341234")
                .password(passwordEncoder.encode("tester1234"))
                .email("tester@test.com")
                .build();
        userRepository.save(user);

        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .userId("tester")
                .password("wrongpassword")
                .build();

        // When
        User loggedInUser = authService.login(loginRequestDto);

        // Then
        assertThat(loggedInUser).isNull();
    }

    @Test
    void givenWrongUserId_whenLoginUser_thenReturnNull() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .userId("tester")
                .username("김테스트")
                .phoneNumber("01012341234")
                .password(passwordEncoder.encode("tester1234"))
                .email("tester@test.com")
                .build();
        userRepository.save(user);

        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .userId("wronguserid")
                .password("tester1234")
                .build();

        // When
        User loggedInUser = authService.login(loginRequestDto);

        // Then
        assertThat(loggedInUser).isNull();
    }
}
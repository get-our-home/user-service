package com.getourhome.userservice;

import com.getourhome.userservice.util.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceApplicationTests {
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Test
    void contextLoads() {
    }

}

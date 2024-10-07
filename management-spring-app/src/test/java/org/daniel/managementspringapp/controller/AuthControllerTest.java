package org.daniel.managementspringapp.controller;

import org.daniel.managementspringapp.dto.LoginRequestDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.service.impl.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Test
    void shouldAuthenticateUser() {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUsername("user1");
        loginRequest.setPassword("password");

        UserDto expectedResponse = UserDto.builder()
                .id(1L)
                .username("user1")
                .build();

        when(authService.authenticateUser(any(LoginRequestDto.class))).thenReturn(ResponseEntity.ok(expectedResponse));
    }

}

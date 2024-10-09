package org.daniel.managementspringapp.service;

import org.daniel.managementspringapp.dto.LoginRequestDto;
import org.daniel.managementspringapp.dto.NewUserDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.security.UserPrincipal;
import org.daniel.managementspringapp.security.jwt.JwtUtils;
import org.daniel.managementspringapp.service.impl.AuthService;
import org.daniel.managementspringapp.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserService userService;

    @Mock
    private UserPrincipal userPrincipal;

    @InjectMocks
    private AuthService authService;

    private LoginRequestDto loginRequest;
    private NewUserDto newUserDto;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestDto();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        newUserDto = new NewUserDto();
        newUserDto.setUsername("newuser");
        newUserDto.setPassword("newpassword");

        authentication = mock(Authentication.class);
    }

    @Test
    void testAuthenticateUser() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(userPrincipal.getId()).thenReturn(1L);
        when(userPrincipal.getUsername()).thenReturn("testuser");

        ResponseCookie jwtCookie = ResponseCookie.from("token", "jwt-token").build();
        when(jwtUtils.generateJwtCookie(userPrincipal)).thenReturn(jwtCookie);

        ResponseEntity<UserDto> response = authService.authenticateUser(loginRequest);

        assertNotNull(response);
        assertEquals("testuser", response.getBody().getUsername());
        assertEquals(1L, response.getBody().getId());
        assertEquals(jwtCookie.toString(), response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateJwtCookie(userPrincipal);
    }

    @Test
    void testRegisterUser() {
        String result = authService.registerUser(newUserDto);

        assertEquals("User registered successfully", result);

        verify(userService, times(1)).add(newUserDto);
    }

    @Test
    void testLogoutUser() {
        ResponseCookie cleanCookie = ResponseCookie.from("token", "").build();
        when(jwtUtils.getCleanJwtCookie()).thenReturn(cleanCookie);

        ResponseCookie result = authService.logoutUser();

        assertEquals(cleanCookie, result);

        verify(jwtUtils, times(1)).getCleanJwtCookie();
    }
}

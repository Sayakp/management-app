package org.daniel.managementspringapp.service;

import org.daniel.managementspringapp.dto.NewUserDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.exception.ResourceNotFoundException;
import org.daniel.managementspringapp.exception.UserAlreadyExistsException;
import org.daniel.managementspringapp.mapper.UserMapper;
import org.daniel.managementspringapp.model.User;
import org.daniel.managementspringapp.repository.UserRepository;
import org.daniel.managementspringapp.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;
    private NewUserDto newUserDto;

    @BeforeEach
    void init() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testuser");

        newUserDto = new NewUserDto();
        newUserDto.setUsername("testuser");
        newUserDto.setPassword("password");
    }

    @Test
    void testGetAll() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.fromUserList(any())).thenReturn(Arrays.asList(userDto));

        List<UserDto> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals(userDto.getUsername(), result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGet() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.fromUser(user)).thenReturn(userDto);

        UserDto result = userService.get(1L);

        assertEquals(userDto.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testAdd() {
        when(userRepository.findByUsername(newUserDto.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(newUserDto.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toUser(newUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.fromUser(user)).thenReturn(userDto);

        UserDto result = userService.add(newUserDto);

        assertEquals(userDto.getUsername(), result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddUserAlreadyExists() {
        when(userRepository.findByUsername(newUserDto.getUsername())).thenReturn(user);

        String expectedMessage = String.format("User already exists with username: %s", user.getUsername());
        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> userService.add(newUserDto));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.get(1L));
        assertEquals("User not found", exception.getMessage());
    }
}

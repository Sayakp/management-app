package org.daniel.managementspringapp.service.impl;

import org.daniel.managementspringapp.dto.NewUserDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.exception.ResourceNotFoundException;
import org.daniel.managementspringapp.exception.UserAlreadyExistsException;
import org.daniel.managementspringapp.mapper.UserMapper;
import org.daniel.managementspringapp.model.User;
import org.daniel.managementspringapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAll() {
        return userMapper.fromUserList(userRepository.findAll());
    }

    public UserDto get(Long id) {
        return userMapper.fromUser(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found"))
        );
    }

    public UserDto add(NewUserDto userDto) {
        Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()))
                .ifPresent(user -> {
                            throw new UserAlreadyExistsException(user.getUsername());
                        }
                );

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User userToSave = userMapper.toUser(userDto);
        userToSave.setEnabled(true);
        userToSave.setAccountNonExpired(true);
        userToSave.setCredentialsNonExpired(true);
        userToSave.setAccountNonLocked(true);

        User savedUser = userRepository.save(userToSave);

        return userMapper.fromUser(savedUser);
    }
}

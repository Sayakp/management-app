package org.daniel.managementspringapp.controller;

import org.daniel.managementspringapp.dto.NewUserDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }
    
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping()
    public UserDto saveUser(@RequestBody NewUserDto userDto) {
        return userService.add(userDto);
    }
}

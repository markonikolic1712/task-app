package com.marko.tasks_app.controller;

import com.marko.tasks_app.domain.CreateUserRequest;
import com.marko.tasks_app.domain.UpdateUserRequest;
import com.marko.tasks_app.domain.dto.CreateUserRequestDto;
import com.marko.tasks_app.domain.dto.UpdateUserRequestDto;
import com.marko.tasks_app.domain.dto.UserDto;
import com.marko.tasks_app.domain.entity.User;
import com.marko.tasks_app.mapper.UserMapper;
import com.marko.tasks_app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> createUer(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        CreateUserRequest createUserRequest = userMapper.fromDto(createUserRequestDto);
        User newUser = userService.createUser(createUserRequest);
        UserDto dto = userMapper.toDto(newUser);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> geetUser(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        UserDto dto = userMapper.toDto(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> gertAllUsers() {
        List<User> users = userService.getUsers();
        List<UserDto> dtoList = users.stream().map(userMapper::toDto).toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto, @PathVariable UUID userId) {
        UpdateUserRequest updateUserRequest = userMapper.fromDto(updateUserRequestDto);
        User user = userService.updateUser(userId, updateUserRequest);
        UserDto dto = userMapper.toDto(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.marko.tasks_app.service;

import com.marko.tasks_app.domain.CreateUserRequest;
import com.marko.tasks_app.domain.UpdateUserRequest;
import com.marko.tasks_app.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(CreateUserRequest createUserRequest);
    User getUserById(UUID id);
    List<User> getUsers();
    User updateUser(UUID id, UpdateUserRequest updateUserRequest);
    void deleteUser(UUID id);
    User getUserByUsername(String username);
}

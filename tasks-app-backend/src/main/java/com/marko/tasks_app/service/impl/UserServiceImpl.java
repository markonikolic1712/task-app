package com.marko.tasks_app.service.impl;

import com.marko.tasks_app.domain.CreateUserRequest;
import com.marko.tasks_app.domain.UpdateUserRequest;
import com.marko.tasks_app.domain.entity.User;
import com.marko.tasks_app.exception.UserNotFoundException;
import com.marko.tasks_app.repository.UserRepository;
import com.marko.tasks_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest createUserRequest) {
        Instant instant = Instant.now();
        User user = new User();
        user.setId(null);
        user.setUsername(createUserRequest.username());
        user.setPassword(createUserRequest.password());
        user.setUpdated(instant);
        user.setCreated(instant);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> optional = userRepository.findById(id);
        return unwrapUser(optional, id, null);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
    }

    @Override
    public User updateUser(UUID id, UpdateUserRequest updateUserRequest) {
        User user = unwrapUser(userRepository.findById(id), id, null);
        user.setUsername(updateUserRequest.username());
        user.setPassword(updateUserRequest.password());
        user.setUpdated(Instant.now());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return unwrapUser(userRepository.findUserByUsername(username), null, username);
    }


    static User unwrapUser(Optional<User> optional, UUID id, String username) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException(id, username);
        }
    }
}

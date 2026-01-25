package com.marko.tasks_app.mapper.impl;

import com.marko.tasks_app.domain.CreateUserRequest;
import com.marko.tasks_app.domain.UpdateUserRequest;
import com.marko.tasks_app.domain.dto.CreateUserRequestDto;
import com.marko.tasks_app.domain.dto.UpdateUserRequestDto;
import com.marko.tasks_app.domain.dto.UserDto;
import com.marko.tasks_app.domain.entity.User;
import com.marko.tasks_app.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public CreateUserRequest fromDto(CreateUserRequestDto dto) {
        return new CreateUserRequest(
                dto.username(),
                dto.password()
        );
    }

    @Override
    public UpdateUserRequest fromDto(UpdateUserRequestDto dto) {
        return new UpdateUserRequest(
                dto.username(),
                dto.password()
        );
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword()
                //user.getCreated(),
                //user.getUpdated()
        );
    }
}

package com.marko.tasks_app.mapper;

import com.marko.tasks_app.domain.CreateUserRequest;
import com.marko.tasks_app.domain.UpdateUserRequest;
import com.marko.tasks_app.domain.dto.*;
import com.marko.tasks_app.domain.entity.User;

public interface UserMapper {
    CreateUserRequest fromDto(CreateUserRequestDto dto);

    UpdateUserRequest fromDto(UpdateUserRequestDto dto);

    UserDto toDto(User user);
}

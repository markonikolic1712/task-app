package com.marko.tasks_app.domain.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String password
) {
}

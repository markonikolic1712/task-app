package com.marko.tasks_app.domain;

public record CreateUserRequest(
        String username,
        String password
) {
}

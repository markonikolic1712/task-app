package com.marko.tasks_app.domain;

public record UpdateUserRequest(
        String username,
        String password
) {
}

package com.marko.tasks_app.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private final UUID id;
    private final String username;

    public UserNotFoundException(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }}

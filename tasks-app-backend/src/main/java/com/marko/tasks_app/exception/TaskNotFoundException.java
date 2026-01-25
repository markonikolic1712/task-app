package com.marko.tasks_app.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException{

    private final UUID id;

    public TaskNotFoundException(UUID id) {
        //super(String.format("Task with ID '%s' does not exists.", id));
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

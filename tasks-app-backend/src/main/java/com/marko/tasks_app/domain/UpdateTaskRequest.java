package com.marko.tasks_app.domain;

import com.marko.tasks_app.domain.entity.TaskPriority;
import com.marko.tasks_app.domain.entity.TaskStatus;

import java.time.LocalDate;

public record UpdateTaskRequest(
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}

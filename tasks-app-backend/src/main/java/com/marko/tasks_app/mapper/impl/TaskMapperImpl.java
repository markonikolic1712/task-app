package com.marko.tasks_app.mapper.impl;

import com.marko.tasks_app.domain.CreateTaskRequest;
import com.marko.tasks_app.domain.UpdateTaskRequest;
import com.marko.tasks_app.domain.dto.CreateTaskRequestDto;
import com.marko.tasks_app.domain.dto.TaskDto;
import com.marko.tasks_app.domain.dto.UpdateTaskRequestDto;
import com.marko.tasks_app.domain.entity.Task;
import com.marko.tasks_app.mapper.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public CreateTaskRequest fromDto(CreateTaskRequestDto dto) {
        return new CreateTaskRequest(
                dto.title(),
                dto.description(),
                dto.dueDate(),
                dto.priority(),
                dto.username()
        );
    }

    @Override
    public UpdateTaskRequest fromDto(UpdateTaskRequestDto dto) {
        return new UpdateTaskRequest(
                dto.title(),
                dto.description(),
                dto.dueDate(),
                dto.status(),
                dto.priority()
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getUser().getUsername()
//                task.getCreated(),
//                task.getUpdated()
        );
    }
}

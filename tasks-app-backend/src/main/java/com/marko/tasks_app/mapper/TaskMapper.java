package com.marko.tasks_app.mapper;

import com.marko.tasks_app.domain.CreateTaskRequest;
import com.marko.tasks_app.domain.UpdateTaskRequest;
import com.marko.tasks_app.domain.dto.CreateTaskRequestDto;
import com.marko.tasks_app.domain.dto.TaskDto;
import com.marko.tasks_app.domain.dto.UpdateTaskRequestDto;
import com.marko.tasks_app.domain.entity.Task;

public interface TaskMapper {
    // iz CreateTaskRequestDto se mapira u CreateTaskRequest
    // od klijenta dolazi CreateTaskRequestDto i on se mapira u CreateTaskRequest a nakon toga CreateTaskRequest ide u aplikaciju
    CreateTaskRequest fromDto(CreateTaskRequestDto dto);

    UpdateTaskRequest fromDto(UpdateTaskRequestDto dto);

    TaskDto toDto(Task task);
}

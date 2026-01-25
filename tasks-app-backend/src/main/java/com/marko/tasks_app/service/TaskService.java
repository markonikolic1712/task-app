package com.marko.tasks_app.service;

import com.marko.tasks_app.domain.CreateTaskRequest;
import com.marko.tasks_app.domain.UpdateTaskRequest;
import com.marko.tasks_app.domain.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TaskService {
    Task createTask(CreateTaskRequest createTaskRequest);
    Task getTaskById(UUID id);
    List<Task> getTasks();
    Task updateTaskById(UUID id, UpdateTaskRequest updateTaskRequest);
    void deleteTask(UUID id);
    List<Task> getTaskListByUserId(UUID userId);
    List<Task> getTaskListByUsername(String username);
}

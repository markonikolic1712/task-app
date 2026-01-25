package com.marko.tasks_app.controller;

import com.marko.tasks_app.domain.CreateTaskRequest;
import com.marko.tasks_app.domain.UpdateTaskRequest;
import com.marko.tasks_app.domain.dto.CreateTaskRequestDto;
import com.marko.tasks_app.domain.dto.TaskDto;
import com.marko.tasks_app.domain.dto.UpdateTaskRequestDto;
import com.marko.tasks_app.domain.entity.Task;
import com.marko.tasks_app.mapper.TaskMapper;
import com.marko.tasks_app.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequestDto taskRequestDto) {
        CreateTaskRequest createTaskRequest = taskMapper.fromDto(taskRequestDto);
        Task newTask = taskService.createTask(createTaskRequest);
        TaskDto dtoForClient = taskMapper.toDto(newTask);

        return new ResponseEntity<>(dtoForClient, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID taskId) {
        Task task = taskService.getTaskById(taskId);
        TaskDto dtoForClient = taskMapper.toDto(task);
        return new ResponseEntity<>(dtoForClient, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTaskList() {
        List<Task> tasks = taskService.getTasks();
        List<TaskDto> dtoList = tasks.stream().map(taskMapper::toDto).toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/user-id/{userId}")
    public ResponseEntity<List<TaskDto>> getAllTasksForUser(@PathVariable UUID userId) {
        List<Task> tasks = taskService.getTaskListByUserId(userId);
        List<TaskDto> dtoList = tasks.stream().map(taskMapper::toDto).toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/username/{username}")
    public ResponseEntity<List<TaskDto>> getAllTasksForUser(@PathVariable String username) {
        List<Task> tasks = taskService.getTaskListByUsername(username);
        List<TaskDto> dtoList = tasks.stream().map(taskMapper::toDto).toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PutMapping(path = "/{taskId}")
    public ResponseEntity<TaskDto> updateTaskById(@Valid @RequestBody UpdateTaskRequestDto updateTaskRequestDto, @PathVariable UUID taskId) {
        UpdateTaskRequest updateTaskRequest = taskMapper.fromDto(updateTaskRequestDto);
        Task updatedTask = taskService.updateTaskById(taskId, updateTaskRequest);

        TaskDto dtoForClient = taskMapper.toDto(updatedTask);
        return new ResponseEntity<>(dtoForClient, HttpStatus.OK);
    }
}

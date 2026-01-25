package com.marko.tasks_app.service.impl;

import com.marko.tasks_app.domain.CreateTaskRequest;
import com.marko.tasks_app.domain.UpdateTaskRequest;
import com.marko.tasks_app.domain.entity.Task;
import com.marko.tasks_app.domain.entity.TaskStatus;
import com.marko.tasks_app.domain.entity.User;
import com.marko.tasks_app.exception.TaskNotFoundException;
import com.marko.tasks_app.repository.TaskRepository;
import com.marko.tasks_app.service.TaskService;
import com.marko.tasks_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    @Override
    public Task createTask(CreateTaskRequest request) {
        Instant now = Instant.now();
        Task task = new Task();
        task.setId(null);
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(request.priority());
        task.setCreated(now);
        task.setUpdated(now);

        User user = userService.getUserByUsername(request.username());
        task.setUser(user);

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(UUID id) {
        Optional<Task> optional = taskRepository.findById(id);
        return unwrapTask(optional, id);
    }

    @Override
    public List<Task> getTasks() {
        // kada se vraca lista svih taskova oni se redjaju po redosledu od najstarijeg do najnovijeg
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
    }

    @Override
    public Task updateTaskById(UUID id, UpdateTaskRequest updateTaskRequest) {
        // uzme se task po id-u a nakon toga mu se setuju svi podaci iz taska koji je poslao klijent. Na krajuse to snimi u bazu
        Task task = unwrapTask(taskRepository.findById(id), id);
        task.setTitle(updateTaskRequest.title());
        task.setDescription(updateTaskRequest.description());
        task.setDueDate(updateTaskRequest.dueDate());
        task.setStatus(updateTaskRequest.status());
        task.setPriority(updateTaskRequest.priority());
        task.setUpdated(Instant.now());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTaskListByUserId(UUID userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public List<Task> getTaskListByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return taskRepository.findAllByUserId(user.getId());
    }


    static Task unwrapTask(Optional<Task> optional, UUID id) {
        if(optional.isPresent()) {
            return optional.get();
        } else {
            System.out.println("unwrapTask() - optional.isPresent(): " + optional.isPresent());
            throw new TaskNotFoundException(id);
        }
    }
}

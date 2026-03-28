package com.marko.tasks_app.controller;

import com.marko.tasks_app.domain.dto.ErrorDto;
import com.marko.tasks_app.exception.TaskNotFoundException;
import com.marko.tasks_app.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

// sa @ControllerAdvice spring boot zna da je ova klasa za hendlovanje izuzetaka
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        UUID userNotFoundId = ex.getId();
        String userNotFoundUsername = ex.getUsername();

        String errorMessage = null;

        if (userNotFoundId != null) {
            errorMessage = String.format("User with ID '%s' does not exists.", userNotFoundId);
        }

        if (userNotFoundUsername != null) {
            errorMessage = String.format("User with username '%s' does not exists.", userNotFoundUsername);
        }

        ErrorDto errorDto = new ErrorDto(errorMessage);

        log.error(errorMessage);

        // vraca se http status 400 jer je greska na strani klijenta
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskNotFoundException(TaskNotFoundException ex) {
        UUID taskNotFoundId = ex.getId();
        String errorMessage = String.format("Task with ID '%s' does not exists.", taskNotFoundId);
        ErrorDto errorDto = new ErrorDto(errorMessage);

        log.error(errorMessage);

        // vraca se http status 400 jer je greska na strani klijenta
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(x -> x.getDefaultMessage())
                .orElse("Validation failed.");

        log.error(errorMessage);

        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}

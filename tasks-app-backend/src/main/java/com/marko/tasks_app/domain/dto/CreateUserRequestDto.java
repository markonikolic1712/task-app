package com.marko.tasks_app.domain.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequestDto(
        @NotBlank(message = ERROR_MESSAGE_USERNAME_LENGTH)
        @Length(min = 5, max = 10, message = ERROR_MESSAGE_USERNAME_LENGTH)
        String username,

        @NotBlank(message = ERROR_MESSAGE_PASSWORD_LENGTH)
        @Length(min = 5, max = 10, message = ERROR_MESSAGE_PASSWORD_LENGTH)
        String password
) {
    private static final String ERROR_MESSAGE_USERNAME_LENGTH = "Username must be between 5 and 10 characters";
    private static final String ERROR_MESSAGE_PASSWORD_LENGTH = "Password must be between 5 and 10 characters";
}

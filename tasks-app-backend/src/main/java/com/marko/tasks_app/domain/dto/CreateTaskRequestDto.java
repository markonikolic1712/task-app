package com.marko.tasks_app.domain.dto;

import com.marko.tasks_app.domain.entity.TaskPriority;
import com.marko.tasks_app.domain.entity.TaskStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

// CreateTaskRequestDto sluzi za prenos informacija do presentation (controller) layer-a. Kada klijent posalje request u body-u ce biti objekat tipa CreateTaskRequestDto
// u CreateTaskRequestDto su definisana pravila za validaciju podataka koji dolaze
// kada podaci dodju u obliku CreateTaskRequestDto u kontroleru se mapiraju u CreateTaskRequest i objekat tipa CreateTaskRequest se salje u servis
// servis vraca entitet Task koji se mapira u TaskDto i klijentu se salje objekat tipa TaskDto
// u CreateTaskRequest se ne prosledjuje status jer kada se kreira task status je uvek OPEN
public record CreateTaskRequestDto(
        @NotBlank(message = ERROR_MESSAGE_TITLE_LENGTH) // nije null i nije prazan string
        @Length(max = 255, message = ERROR_MESSAGE_TITLE_LENGTH)
        String title,

        @Length(max = 1000, message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
        @Nullable   // moze da bude null
        String description,

        @FutureOrPresent(message = ERROR_MESSAGE_DUE_DATE_FUTURE) // datum mora da bude danasnji ili u buducnosti
        @Nullable
        LocalDate dueDate,

        @NotNull(message = ERROR_MESSAGE_PRIORITY)
        TaskPriority priority,

        @NotNull(message = ERROR_MESSAGE_USERNAME)
        String username
) {
    private static final String ERROR_MESSAGE_TITLE_LENGTH = "Title must be between 1 and 255 characters";
    private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH = "Description must be less than characters";
    private static final String ERROR_MESSAGE_DUE_DATE_FUTURE = "Due date must be in future";
    private static final String ERROR_MESSAGE_PRIORITY = "Task priority mus be provided";
    private static final String ERROR_MESSAGE_USERNAME = "Username is mandatory";
}

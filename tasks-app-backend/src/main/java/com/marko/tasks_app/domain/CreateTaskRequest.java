package com.marko.tasks_app.domain;

import com.marko.tasks_app.domain.entity.TaskPriority;

import java.time.LocalDate;

// CreateTaskRequestDto sluzi za prenos informacija do presentation (controller) layer-a. Kada klijent posalje request u body-u ce biti objekat tipa CreateTaskRequestDto
// u CreateTaskRequestDto su definisana pravila za validaciju podataka koji dolaze
// kada podaci dodju u obliku CreateTaskRequestDto u kontroleru se mapiraju u CreateTaskRequest i objekat tipa CreateTaskRequest se salje u servis
// servis vraca entitet Task koji se mapira u TaskDto i klijentu se salje objekat tipa TaskDto
// u CreateTaskRequest se ne prosledjuje status jer kada se kreira task status je uvek OPEN
public record CreateTaskRequest(
        String title,
        String description,
        LocalDate dueDate,
        TaskPriority priority,
        String username
) {
}

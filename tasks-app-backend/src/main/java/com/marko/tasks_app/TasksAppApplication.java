package com.marko.tasks_app;

import com.marko.tasks_app.domain.entity.Task;
import com.marko.tasks_app.domain.entity.TaskPriority;
import com.marko.tasks_app.domain.entity.TaskStatus;
import com.marko.tasks_app.domain.entity.User;
import com.marko.tasks_app.repository.TaskRepository;
import com.marko.tasks_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;

@SpringBootApplication
public class TasksAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TasksAppApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskRepository taskRepository;

	@Override
	public void run(String... args) throws Exception {
		User[] users = new User[] {
				new User(null, "pera", "pera", Instant.now(), Instant.now(), null),
				new User(null, "mika", "mika", Instant.now(), Instant.now(), null),
				new User(null, "zika", "zika", Instant.now(), Instant.now(), null)
		};

		for(User user : users) {
			System.out.println(userRepository.save(user).getId());
		}

		Task[] tasks = new Task[] {
				new Task(null,"Task 1", "Task 1 description", LocalDate.now(), TaskStatus.OPEN, TaskPriority.HIGH, Instant.now(), Instant.now(), users[0]),
				new Task(null,"Task 2", "Task 2 description", LocalDate.parse("2026-03-14"), TaskStatus.COMPLETED, TaskPriority.LOW, Instant.now(), Instant.now(), users[0]),
				new Task(null,"Task 3", "Task 3 description", LocalDate.parse("2026-06-17"), TaskStatus.COMPLETED, TaskPriority.MEDIUM, Instant.now(), Instant.now(), users[0]),
				new Task(null,"Task 4", "Task 4 description", LocalDate.parse("2026-05-05"), TaskStatus.OPEN, TaskPriority.HIGH, Instant.now(), Instant.now(), users[1]),
				new Task(null,"Task 5", "Task 5 description", LocalDate.parse("2026-04-28"), TaskStatus.OPEN, TaskPriority.MEDIUM, Instant.now(), Instant.now(), users[2])
		};

		for(Task task : tasks) {
			taskRepository.save(task);
		}
	}
}

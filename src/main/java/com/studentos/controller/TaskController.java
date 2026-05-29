package com.studentos.controller;

import com.studentos.model.Task;
import com.studentos.service.TaskService;
import org.springframework.core.task.TaskTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// @RestController = @Controller + @ResponseBody
// Every method return value is written directly to the HTTP response body as JSON
// (Spring uses Jackson library to convert Java objects to JSON automatically)
@RestController
@RequestMapping("/api/tasks") // all endpoints in this class start with /api/tasks
public class TaskController {

    // Constructor injection - Spring provides TaskService bean
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks -> return all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks); // 200 OK with tasks as JSON body
    }

    // GET /api/tasks/1 -> return task with id 1
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        Optional<Task> task = taskService.getTaskById(id);

        // If present: return 200 with task
        // If not: return 404 - the resource does not exist
        return task
                .map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // POST /api/tasks -> create a new task
    // @RequestBody tells Spring: parse the JSON request body into a Task object
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created
    }

    // PATCH /api/tasks/1/complete -> mark task as complete
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Integer id) {
        Optional<Task> completed = taskService.completeTask(id);
        return completed
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tasks/1 -> delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        boolean deleted = taskService.deleteTask(id);
        if(deleted) {
            return ResponseEntity.noContent().build(); // 204 - No Content - success, nothing to return
        }
        return ResponseEntity.notFound().build(); // 404 - nothing was there to delete
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks() {
        return ResponseEntity.ok(taskService.getPendingTasks());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }
}

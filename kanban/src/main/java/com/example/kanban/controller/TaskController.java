package com.example.kanban.controller;

import com.example.kanban.model.Task;
import com.example.kanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/grouped")
    public ResponseEntity<Map<Task.TaskStatus, List<Task>>> getTasksGroupedByStatus() {
        Map<Task.TaskStatus, List<Task>> groupedTasks = taskService.getTasksGroupedByStatus();
        return new ResponseEntity<>(groupedTasks, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatusOrdered (status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/filter/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Task.Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> overdueTasks = taskService.getOverdueTasks();
        return new ResponseEntity<>(overdueTasks, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Map<Task.TaskStatus, List<Task>>> generateReport() {
        Map<Task.TaskStatus, List<Task>> report = taskService.generateReport();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<Void> moveTask(@PathVariable Long id) {
        taskService.moveTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
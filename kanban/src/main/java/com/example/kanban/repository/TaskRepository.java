package com.example.kanban.repository;

import com.example.kanban.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatusOrderByPriorityAsc(Task.TaskStatus status);
    List<Task> findByPriority(Task.Priority priority);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDateTime dueDate, Task.TaskStatus status);
}
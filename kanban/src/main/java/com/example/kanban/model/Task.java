package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    // Data de criação gerada automaticamente
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDateTime dueDate;


    public enum TaskStatus {
        TODO, IN_PROGRESS, DONE, OVERDUE
    }


    public enum Priority {
        BAIXA, MEDIA, ALTA
    }
}
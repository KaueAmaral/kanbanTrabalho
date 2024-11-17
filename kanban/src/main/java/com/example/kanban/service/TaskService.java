package com.example.kanban.service;

import com.example.kanban.model.Task;
import com.example.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void moveTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (task.getStatus() == Task.TaskStatus.TODO) {
            task.setStatus(Task.TaskStatus.IN_PROGRESS);
        } else if (task.getStatus() == Task.TaskStatus.IN_PROGRESS) {
            task.setStatus(Task.TaskStatus.DONE);
        }
        taskRepository.save(task);
    }

    public Map<Task.TaskStatus, List<Task>> getTasksGroupedByStatus() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
    }

    public List<Task> getTasksByStatusOrdered(Task.TaskStatus status) {
        return taskRepository.findByStatusOrderByPriorityAsc(status);
    }

    public List<Task> getTasksByPriority(Task.Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findByDueDateBeforeAndStatusNot(now, Task.TaskStatus.DONE);
    }

    public Map<Task.TaskStatus, List<Task>> generateReport() {
        Map<Task.TaskStatus, List<Task>> report = getTasksGroupedByStatus();
        List<Task> overdueTasks = getOverdueTasks();
        report.put(Task.TaskStatus.DONE, overdueTasks); // Adiciona as tarefas atrasadas
        return report;
    }
}
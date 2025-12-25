package com.todo.service;

import com.todo.entity.Task;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        task.setId(null);  // 确保新建
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("任务不存在"));

        // 只更新允许的字段
        if (task.getTitle() != null) {
            existing.setTitle(task.getTitle());
        }
        if (task.getDescription() != null) {
            existing.setDescription(task.getDescription());
        }
        if (task.getCompleted() != null) {
            existing.setCompleted(task.getCompleted());
        }

        return taskRepository.save(existing);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}

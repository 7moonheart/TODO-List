package com.todo.controller;

import com.todo.entity.Task;
import com.todo.repository.TaskRepository;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")  // 简单粗暴允许跨域
public class TaskController {

//    @Autowired
//    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    // 1. 获取所有任务
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 2. 创建任务
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // 3. 更新任务（标记完成/未完成）
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
//        task.setId(id);
//        return taskRepository.save(task);
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("任务不存在"));

        // 只更新非null的字段
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

    // 4. 删除任务
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}


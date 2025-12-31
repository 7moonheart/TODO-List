package com.todo.controller;

import com.todo.entity.Task;
import com.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // 获取所有任务
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(createSuccessResponse(tasks));
    }

    // 获取任务详情
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTask(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(createSuccessResponse(task)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse(404, "任务不存在")));
    }

    // 创建任务
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Task task) {
        // 验证必填字段
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(400, "任务标题不能为空"));
        }

        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createSuccessResponse(created));
    }

    // 更新任务
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTask(
            @PathVariable Long id,
            @RequestBody Task taskUpdate) {

        return taskService.updateTask(id, taskUpdate)
                .map(updated -> ResponseEntity.ok(createSuccessResponse(updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse(404, "任务不存在")));
    }

    // 删除任务
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(404, "任务不存在"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "删除成功");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

    // 切换任务状态
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Map<String, Object>> toggleTask(@PathVariable Long id) {
        boolean toggled = taskService.toggleTaskStatus(id);
        if (!toggled) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(404, "任务不存在"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "状态切换成功");
        return ResponseEntity.ok(response);
    }

    // 搜索任务
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTasks(
            @RequestParam String keyword) {
        List<Task> tasks = taskService.searchTasks(keyword);
        return ResponseEntity.ok(createSuccessResponse(tasks));
    }

    // 获取未完成任务
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveTasks() {
        List<Task> tasks = taskService.getActiveTasks();
        return ResponseEntity.ok(createSuccessResponse(tasks));
    }

    // 获取已完成任务
    @GetMapping("/completed")
    public ResponseEntity<Map<String, Object>> getCompletedTasks() {
        List<Task> tasks = taskService.getCompletedTasks();
        return ResponseEntity.ok(createSuccessResponse(tasks));
    }

    // 获取统计信息
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = taskService.getStatistics();
        return ResponseEntity.ok(createSuccessResponse(stats));
    }

    // 批量操作
    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchOperation(
            @RequestBody Map<String, Object> request) {

        String operation = (String) request.get("operation");
        List<Long> ids = (List<Long>) request.get("ids");

        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(400, "请选择任务"));
        }

        int result = 0;
        String message = "";

        switch (operation) {
            case "delete":
                result = taskService.batchDelete(ids);
                message = "批量删除成功";
                break;
            case "complete":
                result = taskService.batchMarkAsCompleted(ids);
                message = "批量标记完成";
                break;
            default:
                return ResponseEntity.badRequest()
                        .body(createErrorResponse(400, "不支持的操作类型"));
        }

        Map<String, Object> response = createSuccessResponse(null);
        response.put("affected", result);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    // 成功响应
    private Map<String, Object> createSuccessResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "成功");
        response.put("data", data);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    // 错误响应
    private Map<String, Object> createErrorResponse(int code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", null);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}
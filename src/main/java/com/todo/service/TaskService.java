package com.todo.service;

import com.todo.entity.Task;
import com.todo.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    // 获取所有任务
    public List<Task> getAllTasks() {
        return taskMapper.findAll();
    }

    // 根据ID获取任务
    public Task getTaskById(Long id) {
        return taskMapper.findById(id);
    }

    // 根据标签获取任务
    public List<Task> getTasksByTag(String tag) {
        return taskMapper.findByTag(tag);
    }

    // 创建任务
    @Transactional
    public Task createTask(Task task) {
        LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        task.setCompleted(false);
        task.setDeleted(false);
        if (task.getTag() == null) {
            task.setTag("work");
        }

        taskMapper.insert(task);
        return task;
    }

    // 更新任务
    @Transactional
    public boolean updateTask(Long id, Task taskUpdate) {
        Task existing = taskMapper.findById(id);
        if (existing == null) {
            return false;
        }

        // 更新字段
        if (taskUpdate.getTitle() != null) {
            existing.setTitle(taskUpdate.getTitle());
        }
        if (taskUpdate.getDescription() != null) {
            existing.setDescription(taskUpdate.getDescription());
        }
        if (taskUpdate.getTag() != null) {
            existing.setTag(taskUpdate.getTag());
        }
        if (taskUpdate.getCompleted() != null) {
            existing.setCompleted(taskUpdate.getCompleted());
        }
        if (taskUpdate.getPriority() != null) {
            existing.setPriority(taskUpdate.getPriority());
        }
        if (taskUpdate.getDueDate() != null) {
            existing.setDueDate(taskUpdate.getDueDate());
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return taskMapper.update(existing) > 0;
    }

    // 删除任务
    @Transactional
    public boolean deleteTask(Long id) {
        return taskMapper.softDelete(id) > 0;
    }

    // 搜索任务
    public List<Task> searchTasks(String keyword) {
        return taskMapper.search(keyword);
    }

    // 获取未完成任务
    public List<Task> getActiveTasks() {
        return taskMapper.findByCompleted(false);
    }

    // 获取已完成任务
    public List<Task> getCompletedTasks() {
        return taskMapper.findByCompleted(true);
    }

    // 切换任务状态
    @Transactional
    public boolean toggleTaskStatus(Long id) {
        Task task = taskMapper.findById(id);
        if (task == null) {
            return false;
        }

        Boolean newStatus = !task.getCompleted();
        return taskMapper.toggleStatus(id, newStatus) > 0;
    }

    // 批量删除
    @Transactional
    public int batchDelete(List<Long> ids) {
        int count = 0;
        for (Long id : ids) {
            if (taskMapper.softDelete(id) > 0) {
                count++;
            }
        }
        return count;
    }

    // 批量标记完成
    @Transactional
    public int batchMarkAsCompleted(List<Long> ids) {
        return taskMapper.batchUpdateStatus(ids, true, LocalDateTime.now());
    }

    // 获取统计信息
    public Map<String, Object> getStatistics() {
        // 或者使用多个查询
        long total = taskMapper.countAll();
        long completed = taskMapper.countCompleted();
        long active = total - completed;

        return Map.of(
                "total", total,
                "completed", completed,
                "active", active,
                "dueSoon", 0  // 需要额外查询
        );
    }

    // 条件查询（使用XML映射）
    public List<Task> findByConditions(Map<String, Object> conditions) {
        // List<Task> findByConditions(Map<String, Object> conditions);
        return null; // 临时返回
    }
}
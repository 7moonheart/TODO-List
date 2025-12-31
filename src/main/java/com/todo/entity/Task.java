package com.todo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private Boolean completed = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer priority = 0;
    private LocalDateTime dueDate;
    private Boolean deleted = false;  // 软删除标记

    // 构造器
    public Task() {}

    public Task(String title) {
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 可选：手动设置时间
    public void setCreateAndUpdateTime() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        this.updatedAt = now;
    }
}
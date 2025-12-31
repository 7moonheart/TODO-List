package com.todo.mapper;

import com.todo.entity.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface TaskMapper {

    // 查询所有未删除的任务
    @Select("""
        SELECT id, title, description, completed, 
               created_at as createdAt, updated_at as updatedAt,
               priority, due_date as dueDate, deleted
        FROM todo_tasks 
        WHERE deleted = 0 
        ORDER BY created_at DESC
    """)
    List<Task> findAll();

    // 根据ID查询
    @Select("""
        SELECT id, title, description, completed, 
               created_at as createdAt, updated_at as updatedAt,
               priority, due_date as dueDate, deleted
        FROM todo_tasks 
        WHERE id = #{id} AND deleted = 0
    """)
    Task findById(Long id);

    // 根据标签查询
    @Select("SELECT * FROM todo_tasks WHERE tag = #{tag} AND deleted = 0 ORDER BY created_at DESC")
    List<Task> findByTag(@Param("tag") String tag);

    // 插入任务
    @Insert("""
        INSERT INTO todo_tasks (
            title, description, tag, completed, 
            created_at, updated_at, priority, 
            due_date, deleted
        ) VALUES (
            #{title}, #{description}, #{tag}, #{completed},
            #{createdAt}, #{updatedAt}, #{priority},
            #{dueDate}, #{deleted}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Task task);

    // 更新任务
    @Update("UPDATE todo_tasks SET " +
            "title = #{title}, " +
            "description = #{description}, " +
            "tag = #{tag}, " +
            "completed = #{completed}, " +
            "updated_at = CURRENT_TIMESTAMP " +
            "WHERE id = #{id}")
    int update(Task task);

    // 软删除
    @Update("UPDATE todo_tasks SET deleted = 1 WHERE id = #{id}")
    int softDelete(Long id);

    // 硬删除
    @Delete("DELETE FROM todo_tasks WHERE id = #{id}")
    int hardDelete(Long id);

    // 更新任务状态
    @Update("UPDATE todo_tasks SET completed = #{completed} WHERE id = #{id}")
    int toggleStatus(@Param("id") Long id, @Param("completed") Boolean completed);

    // 根据状态查询
    @Select("""
        SELECT id, title, description, completed, 
               created_at as createdAt, updated_at as updatedAt,
               priority, due_date as dueDate, deleted
        FROM todo_tasks 
        WHERE completed = #{completed} AND deleted = 0
        ORDER BY created_at DESC
    """)
    List<Task> findByCompleted(@Param("completed") Boolean completed);

    // 搜索任务
    @Select("""
        SELECT id, title, description, completed, 
               created_at as createdAt, updated_at as updatedAt,
               priority, due_date as dueDate, deleted
        FROM todo_tasks 
        WHERE (title LIKE CONCAT('%', #{keyword}, '%') 
               OR description LIKE CONCAT('%', #{keyword}, '%'))
          AND deleted = 0
        ORDER BY created_at DESC
    """)
    List<Task> search(@Param("keyword") String keyword);

    // 统计总数
    @Select("SELECT COUNT(*) FROM todo_tasks WHERE deleted = 0")
    Long countAll();

    // 统计已完成
    @Select("SELECT COUNT(*) FROM todo_tasks WHERE completed = 1 AND deleted = 0")
    Long countCompleted();

    // 批量更新状态
    @Update({
            "<script>",
            "UPDATE todo_tasks SET completed = #{completed}, updated_at = #{updatedAt}",
            "WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "AND deleted = 0",
            "</script>"
    })
    int batchUpdateStatus(@Param("ids") List<Long> ids,
                          @Param("completed") Boolean completed,
                          @Param("updatedAt") LocalDateTime updatedAt);
}
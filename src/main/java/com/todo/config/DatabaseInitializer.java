package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== 初始化数据库 ===");

        // 1. 创建data目录
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
            System.out.println("✅ 创建data目录: " + dataDir.getAbsolutePath());
        }

        // 2. 检查数据库文件
        File dbFile = new File("data/todo.db");
        boolean dbExists = dbFile.exists();

        // 3. 执行SQL初始化脚本
        try (Connection conn = dataSource.getConnection()) {
            if (!dbExists) {
                System.out.println("🆕 创建新的数据库文件...");
                // 执行schema.sql
                ClassPathResource schemaResource = new ClassPathResource("schema.sql");
                if (schemaResource.exists()) {
                    ScriptUtils.executeSqlScript(conn, schemaResource);
                    System.out.println("✅ 数据库表结构初始化完成");
                }
            } else {
                System.out.println("✅ 使用现有数据库文件");
            }
        } catch (Exception e) {
            System.err.println("❌ 数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== 数据库初始化完成 ===");
    }
}
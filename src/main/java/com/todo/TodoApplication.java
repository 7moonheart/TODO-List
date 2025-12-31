package com.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
        System.out.println("\n" + "=".repeat(50));
        System.out.println("✅ TODO应用启动成功！");
        System.out.println("🌐 访问地址: http://localhost:8080");
        System.out.println("=".repeat(50) + "\n");
    }
}
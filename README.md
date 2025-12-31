# TODO List 应用

## 🌟 功能特性

- ✅ 待办事项管理
- ✅ 任务详情描述
- ✅ 数据持久化（SQLite）
- ✅ 标签分类（工作/学习/生活）
- ✅ 多种筛选方式（状态、标签）
- ✅ 实时统计信息
## 🚀 快速开始

### 环境要求
- Java 21
- Maven 3.6+
- 现代浏览器
### 运行方式

```bash
# 克隆项目
git clone https://github.com/7moonheart/TODO-List.git
cd todo

# 编译并运行
mvn clean spring-boot:run

# 访问应用
# 浏览器打开: http://localhost:8080
```

### 项目结构
```
todo/
├── src/main/java/com/todo/     # Java 源代码
│   ├── config/                 # 配置类
│   ├── controller/             # 控制器
│   ├── entity/                 # 实体类
│   ├── mapper/                 # MyBatis Mapper
│   └── service/                # 业务逻辑
├── src/main/resources/         # 资源文件
│   ├── static/                 # 静态资源（前端）
│   └── application.yml         # 配置文件
├── data/                       # 数据库文件（运行时生成）
└── logs/                       # 日志文件（运行时生成）
```

## 📖 使用指南

### 添加任务
1. 在输入框中输入任务标题
2. （可选）输入任务详情描述
3. 选择标签分类（工作/学习/生活）
4. 点击"添加任务"按钮

### 管理任务
- **标记完成**：点击任务前的复选框
- **编辑任务**：点击任务卡片上的"编辑"按钮
- **删除任务**：点击任务卡片上的"删除"按钮

### 筛选任务
- **状态筛选**：使用顶部"全部/进行中/已完成"按钮
- **标签筛选**：使用标签筛选按钮（工作/学习/生活）
- **组合筛选**：可以同时使用状态和标签筛选

## 🔧 配置说明

### 数据库配置
应用使用 SQLite 数据库，无需额外安装数据库服务：
- 数据库文件：`data/todo.db`（自动创建）

### 日志配置
- 应用日志：`logs/application.log`
- 控制台日志：实时输出到控制台

### 自定义配置
编辑 `src/main/resources/application.yml`：
```yaml
server:
  port: 8080  # 修改端口号
  
spring:
  datasource:
    url: jdbc:sqlite:data/todo.db  # 数据库路径
    
logging:
  level:
    com.todo: INFO  # 日志级别
```

## 📊 API 接口

### 主要接口
- `GET    /api/tasks` - 获取所有任务
- `GET    /api/tasks/{id}` - 获取任务详情
- `POST   /api/tasks` - 创建新任务
- `PUT    /api/tasks/{id}` - 更新任务
- `DELETE /api/tasks/{id}` - 删除任务
- `PATCH  /api/tasks/{id}/toggle` - 切换任务状态

### 筛选接口
- `GET    /api/tasks/tag/{tag}` - 按标签获取任务

## 🛠️ 开发说明

### 技术栈
- **后端**：Spring Boot 2.7 + MyBatis + SQLite
- **前端**：原生HTML/CSS/JavaScript
- **数据库**：SQLite（轻量级，无需安装）
- **构建工具**：Maven

### 数据库初始化
应用启动时自动创建数据库表结构：
```sql
CREATE TABLE tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    tag TEXT DEFAULT 'work',
    completed BOOLEAN DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT 0
)
```

### 常见问题

#### 1. 端口被占用
```bash
# 修改端口号
java -jar todo-app.jar --server.port=8081
```

#### 2. 数据库文件损坏
```bash
# 备份后删除数据库文件
rm data/todo.db
# 重启应用会自动创建新数据库
```

## 🙏 致谢

感谢以下开源项目：
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis](https://mybatis.org/mybatis-3/)
- [SQLite](https://www.sqlite.org/)

---

访问应用：http://localhost:8080

开始使用吧！🎯
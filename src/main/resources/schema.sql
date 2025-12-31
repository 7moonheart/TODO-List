-- 创建任务表
CREATE TABLE IF NOT EXISTS todo_tasks (
                                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                                          title TEXT NOT NULL,
                                          description TEXT,
                                          completed BOOLEAN NOT NULL DEFAULT 0,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          priority INTEGER DEFAULT 0,
                                          due_date TIMESTAMP,
                                          deleted BOOLEAN DEFAULT 0
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_tasks_completed ON todo_tasks(completed);
CREATE INDEX IF NOT EXISTS idx_tasks_created_at ON todo_tasks(created_at);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date ON todo_tasks(due_date);
CREATE INDEX IF NOT EXISTS idx_tasks_deleted ON todo_tasks(deleted);
CREATE INDEX IF NOT EXISTS idx_tasks_title ON todo_tasks(title);

-- 初始化数据（可选）
INSERT OR IGNORE INTO todo_tasks (title, description, completed, priority) VALUES
('欢迎使用TODO应用', '这是一个示例任务，可以编辑或删除它', 0, 1);
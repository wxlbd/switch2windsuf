# 🚀 快速开始

## 最简单的方法（3 步）

### 1️⃣ 运行安装脚本
```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./install-plugin.sh
```

按照提示选择你使用的 IDE，脚本会自动：
- ✅ 构建插件包（如果需要）
- ✅ 找到 IDE 插件目录
- ✅ 自动安装插件
- ✅ 备份旧版本

### 2️⃣ 重启 IDE
完全关闭并重新打开你的 IDE

### 3️⃣ 验证安装
进入 **Settings/Preferences** → **Plugins** 搜索 "Switch2Windsurf"

---

## 🎯 使用插件

安装后，你就可以使用以下功能：

### 快捷键
- **Alt+Shift+O** - 在 Windsurf 中打开当前文件
- **Alt+Shift+P** - 在 Windsurf 中打开当前项目

### 菜单访问
- 右键编辑器 → "Open File In Windsurf"
- 右键项目视图 → "Open Project In Windsurf"
- Tools 菜单 → 两个 Switch2Windsurf 选项

### 配置
- Settings/Preferences → Tools → Switch2Windsurf
- 设置 Windsurf 可执行文件路径（默认: `windsurf`）

---

## 📋 如果遇到不兼容错误

### 快速修复（推荐）
编辑 `build.gradle.kts` 的第 18-19 行：

```diff
- version.set("2022.3")
+ version.set("2024.1")
```

然后重新构建：
```bash
./gradlew clean buildPlugin
./install-plugin.sh
```

### 详细故障排除
查看完整指南：`LOCAL_BUILD_GUIDE.md`

---

## 🔧 其他有用命令

```bash
# 仅构建（不安装）
./gradlew buildPlugin

# 在测试 IDE 沙箱中运行
./gradlew runIde

# 完整清理和重建
./gradlew clean build

# 查看构建日志
./gradlew buildPlugin -S

# 查看 IDE 日志（调试）
tail -f ~/Library/Logs/JetBrains/*/system.log
```

---

## 📞 获取帮助

- 📖 完整指南：`LOCAL_BUILD_GUIDE.md`
- 🐛 GitHub Issues：https://github.com/wxlbd/switch2windsurf/issues
- ⚙️ IDE 日志位置：
  - macOS: `~/Library/Logs/JetBrains/GoLand/system.log`
  - Linux: `~/.local/share/JetBrains/GoLand/system.log`
  - Windows: `%APPDATA%\JetBrains\GoLand\system\log`

---

**现在就开始吧！** 运行 `./install-plugin.sh` 👆

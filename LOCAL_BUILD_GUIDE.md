# Switch2Windsurf 本地打包和使用指南

## 📦 问题分析

### 为什么插件不兼容？

当前配置中存在以下潜在的兼容性问题：

1. **IDE 基础版本过低**
   - 当前配置：`version.set("2022.3")`
   - 建议：升级到 `2024.1` 或更新版本
   - 原因：新版本 IDE 可能不向后兼容太久远的版本

2. **Java 版本限制**
   - 当前配置：JVM target = 17
   - 新版本 IDE（2024+）可能需要 Java 21

3. **插件构建版本范围**
   - `sinceBuild.set("223")` - 太旧
   - `untilBuild.set("")` - 无上限（可能导致新版本 IDE 警告）

---

## 🚀 本地打包步骤

### 步骤 1：修复兼容性问题（推荐）

编辑 `build.gradle.kts` 提高版本兼容性：

```kotlin
intellij {
    version.set("2024.1")  // 更新到更新的 IDE 版本
    type.set("IC")
    pluginName.set("Switch2Windsurf")
    updateSinceUntilBuild.set(true)
    sameSinceUntilBuild.set(false)
    plugins.set(listOf())
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"  // 可保持 Java 17
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")  // 对应 2024.1
        untilBuild.set("")     // 支持所有新版本
    }
}
```

### 步骤 2：使用现有构建（快速方案）

项目已经构建完成，插件包位置：

```
./build/libs/switch2windsurf-2.0.0.jar
```

### 步骤 3：安装到 IDE

#### 方案 A：通过 IDE 安装（推荐）

1. 打开你的 JetBrains IDE（IntelliJ IDEA、GoLand 等）
2. 进入 **Settings/Preferences** → **Plugins**
3. 点击右上角的 **⚙️ 齿轮图标** → **Install Plugin from Disk...**
4. 选择：`/Users/wxl/GolandProjects/switch2windsuf/build/libs/switch2windsurf-2.0.0.jar`
5. 重启 IDE

#### 方案 B：手动复制（开发者模式）

1. 找到 IDE 插件目录：
   ```bash
   # macOS
   ~/Library/Application Support/JetBrains/<IDE>/plugins/

   # Linux
   ~/.local/share/JetBrains/<IDE>/plugins/

   # Windows
   %APPDATA%\JetBrains\<IDE>\plugins\
   ```

2. 创建目录并复制：
   ```bash
   mkdir -p ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf/lib
   cp ./build/libs/switch2windsurf-2.0.0.jar \
      ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf/lib/
   ```

3. 重启 IDE

---

## 🔧 重新构建（如果修改配置）

```bash
cd /Users/wxl/GolandProjects/switch2windsuf

# 清理旧构建
./gradlew clean

# 构建插件包
./gradlew buildPlugin

# 输出位置
# ./build/distributions/switch2windsurf-2.0.0.zip
```

---

## ✅ 验证安装成功

1. 重启 IDE 后，进入 **Settings/Preferences** → **Plugins**
2. 搜索 "Switch2Windsurf"
3. 应该显示为 **已启用** 状态
4. 检查 **Tools** 菜单，应该看到 "Open File In Windsurf" 和 "Open Project In Windsurf"

---

## 🎯 如果仍然显示不兼容

### 检查清单

```bash
# 1. 查看 IDE 实际版本
# 在 IDE 中：Help → About

# 2. 查看构建日志
cd /Users/wxl/GolandProjects/switch2windsuf
./gradlew buildPlugin -S

# 3. 检查 IDE 兼容性警告
# IDE 插件设置页面会显示具体的兼容性错误信息
```

### 解决不兼容的终极方案

如果上述步骤后仍有问题，执行完整的兼容性升级：

1. **更新 `build.gradle.kts`**
   ```kotlin
   intellij {
       version.set("2024.3")  // 使用最新稳定版本
       type.set("IC")
       pluginName.set("Switch2Windsurf")
   }
   ```

2. **更新 Java 版本**
   ```kotlin
   tasks {
       withType<JavaCompile> {
           sourceCompatibility = "21"
           targetCompatibility = "21"
       }
       withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
           kotlinOptions.jvmTarget = "21"
       }
   }
   ```

3. **更新构建范围**
   ```kotlin
   tasks {
       patchPluginXml {
           sinceBuild.set("243")  // 对应 2024.3
           untilBuild.set("")
       }
   }
   ```

4. **重新构建**
   ```bash
   ./gradlew clean buildPlugin
   ```

---

## 📝 快速命令参考

```bash
# 查看已有的构建产物
ls -lh /Users/wxl/GolandProjects/switch2windsuf/build/libs/

# 完整构建（包含测试）
./gradlew build

# 仅构建插件包
./gradlew buildPlugin

# 在沙箱 IDE 中测试
./gradlew runIde

# 清理所有构建文件
./gradlew clean
```

---

## 💡 常见问题

**Q: 为什么本地构建的插件和发布的插件不同？**
A: 本地构建使用的是 `switch2windsurf-2.0.0.jar`，发布版本可能经过签名和压缩。

**Q: 如何更新插件版本号？**
A: 编辑 `build.gradle.kts` 第 8 行：`version = "2.1.0"`

**Q: 插件在哪些 IDE 中可用？**
A: 所有 JetBrains IDE（IntelliJ IDEA、GoLand、PyCharm、WebStorm 等）

---

## 🔗 相关资源

- [JetBrains 插件开发文档](https://plugins.jetbrains.com/docs/intellij/)
- [IDE 兼容性矩阵](https://plugins.jetbrains.com/docs/intellij/plugin-compatibility.html)
- [Gradle IntelliJ 插件文档](https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html)

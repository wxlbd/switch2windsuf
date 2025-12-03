# 🔍 插件兼容性和故障排除指南

## 不兼容问题的根本原因分析

### 当前配置状态
```gradle
intellij {
    version.set("2022.3")        # ⚠️ 问题 1: IDE 基础版本过低
    type.set("IC")
    pluginName.set("Switch2Windsurf")
}

tasks {
    patchPluginXml {
        sinceBuild.set("223")     # ⚠️ 问题 2: 支持范围太小
        untilBuild.set("")        # ⚠️ 问题 3: 无上限可能导致新版本警告
    }

    withType<JavaCompile> {
        sourceCompatibility = "17" # ⚠️ 问题 4: Java 17 可能过低
        targetCompatibility = "17"
    }
}
```

### 为什么会显示不兼容？

1. **IDE 版本不符**
   - 你的 IDE 版本很可能 ≥ 2024.x
   - 插件配置仅支持从 2022.3 开始
   - **解决：** 更新 `version.set()` 到你的 IDE 版本

2. **Java 版本冲突**
   - 新版本 IDE（2024+）通常需要 Java 21+
   - 当前配置 target Java 17
   - **解决：** 升级到 Java 21

3. **Build 版本范围太窄**
   - `sinceBuild = "223"` 对应 2022.3
   - 现代 IDE 版本号已经到 244+ (2024.4)
   - **解决：** 更新 `sinceBuild` 值

4. **插件配置可能不完整**
   - 缺少某些必要的扩展点声明
   - **解决：** 检查 `plugin.xml` 完整性

---

## 🔧 修复不兼容的三个方案

### 方案 1️⃣：最小改动（推荐快速试用）

**风险级别：低** | **预期效果：中等**

编辑 `build.gradle.kts`，仅更新 sinceBuild：

```kotlin
tasks {
    patchPluginXml {
        sinceBuild.set("231")     // 对应 2023.1
        untilBuild.set("")
    }
}
```

然后重新构建：
```bash
./gradlew clean buildPlugin
./install-plugin.sh
```

---

### 方案 2️⃣：适度升级（推荐大多数用户）

**风险级别：低-中** | **预期效果：高**

编辑 `build.gradle.kts`：

```kotlin
intellij {
    version.set("2024.1")    // 更新基础版本
    type.set("IC")
    pluginName.set("Switch2Windsurf")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")  // 对应 2024.1
        untilBuild.set("")
    }
}
```

执行构建：
```bash
./gradlew clean buildPlugin
./install-plugin.sh
```

---

### 方案 3️⃣：完全现代化（推荐新项目）

**风险级别：中** | **预期效果：最优**

编辑 `build.gradle.kts`：

```kotlin
intellij {
    version.set("2024.3")    // 最新稳定版本
    type.set("IC")
    pluginName.set("Switch2Windsurf")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"  // 升级到 Java 21
        targetCompatibility = "21"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
    }

    patchPluginXml {
        sinceBuild.set("243")  // 对应 2024.3
        untilBuild.set("")
    }
}
```

完整重建：
```bash
./gradlew clean build
./gradlew buildPlugin
./install-plugin.sh
```

---

## 📊 IDE 版本对应表

| IDE 版本 | Build 号 | 发布日期 |
|---------|---------|--------|
| 2022.3  | 223     | 2022-11 |
| 2023.1  | 231     | 2023-03 |
| 2023.2  | 232     | 2023-07 |
| 2024.1  | 241     | 2024-03 |
| 2024.2  | 242     | 2024-07 |
| 2024.3  | 243     | 2024-11 |

**查看你的 IDE 版本：** Help → About → Build number

---

## 🚨 安装后仍然显示不兼容？

### 步骤 1: 收集诊断信息
```bash
# 查看 IDE 版本
echo "检查你的 IDE 版本信息（从 About 对话框）"

# 查看 IDE 日志
tail -50 ~/Library/Logs/JetBrains/GoLand/system.log | grep -i "plugin\|incompatible"

# 检查构建日志
./gradlew buildPlugin -S 2>&1 | grep -i "warn\|error\|plugin"
```

### 步骤 2: 验证插件 XML
```bash
# 查看构建后的 plugin.xml
cat build/patchedPluginXmlFiles/plugin.xml

# 确认 sinceBuild 和 untilBuild
grep -A 2 "patchPluginXml" build.gradle.kts
```

### 步骤 3: 检查缓存问题
```bash
# 完全移除旧插件和缓存
rm -rf ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf
rm -rf ~/Library/Caches/JetBrains/GoLand

# 重新安装
./install-plugin.sh
```

---

## 🔬 高级调试

### 查看详细的不兼容原因
```bash
# 在 IDE 中打开插件管理页面后，查看日志
tail -100f ~/Library/Logs/JetBrains/GoLand/system.log | grep -i switch2windsurf
```

### 验证 plugin.xml 的有效性
```bash
# 检查 XML 语法
xmllint build/patchedPluginXmlFiles/plugin.xml

# 查看完整的 XML 内容
cat build/patchedPluginXmlFiles/plugin.xml | head -50
```

### 测试在 IDE 沙箱中运行
```bash
# 在测试沙箱中启动 IDE（包含调试信息）
./gradlew runIde --debug
```

---

## 🎯 场景化解决方案

### 场景 1: "插件与 IDE 版本不兼容"

**原因：** `sinceBuild` 或 `untilBuild` 不匹配

**解决：**
1. 检查 IDE Help → About 中的 Build 号
2. 更新 `build.gradle.kts` 中的 sinceBuild
3. 执行 `./gradlew clean buildPlugin && ./install-plugin.sh`

### 场景 2: "安装后插件不显示"

**原因：** 插件目录权限问题或安装路径错误

**解决：**
```bash
# 验证安装位置
ls -la ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf/

# 修复权限
chmod -R 755 ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf/

# 重启 IDE
```

### 场景 3: "Java 版本错误"

**原因：** IDE 的 Java 版本与插件配置 target 不匹配

**解决：**
```bash
# 方案 A: 升级插件到 Java 21（推荐）
# 编辑 build.gradle.kts，设置 sourceCompatibility = "21"

# 方案 B: 检查 IDE 使用的 Java 版本
# IDE → Help → About → Check Java Version

# 方案 C: 强制使用特定 Java
export JAVA_HOME="/path/to/java21"
./gradlew clean buildPlugin
```

---

## ✅ 验证清单

运行以下检查确保插件正确配置：

- [ ] IDE 版本已确认
- [ ] `build.gradle.kts` 中的 `sinceBuild` 与 IDE 版本匹配
- [ ] Java 版本兼容（17+ 推荐 21+）
- [ ] 插件 JAR 已构建：`./build/libs/switch2windsurf-2.0.0.jar`
- [ ] 插件已安装到正确目录
- [ ] IDE 已重启
- [ ] 在 Plugins 页面找到 Switch2Windsurf
- [ ] Tools 菜单出现新选项

---

## 📞 如果还是解决不了

1. **收集完整日志**
   ```bash
   ./gradlew clean buildPlugin -S > build_log.txt 2>&1
   tail -100 ~/Library/Logs/JetBrains/GoLand/system.log > ide_log.txt
   ```

2. **检查 GitHub Issues**
   - https://github.com/wxlbd/switch2windsurf/issues

3. **提供以下信息**
   - 你的 IDE 及版本
   - 完整的错误信息（从日志中复制）
   - `gradle.properties` 内容
   - `build.gradle.kts` 内容

---

**提示：** 大多数兼容性问题通过更新 `sinceBuild` 值即可解决！

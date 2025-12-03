# 📚 Switch2Windsurf 本地打包完整指南

## 🎯 核心问题总结

### 为什么显示不兼容？

你的插件在配置中声明只支持从 **IDE 2022.3 开始**，但你可能在使用更新版本的 IDE（2024+）。

**具体原因：**
```gradle
build.gradle.kts 第 18 行: version.set("2022.3")
build.gradle.kts 第 38 行: sinceBuild.set("223")
```

这意味着插件告诉 IDE："我只兼容 2022.3 及以上版本"，但该配置可能过旧。

---

## ✅ 解决方案（按推荐顺序）

### 🥇 推荐方案 1: 自动安装脚本（最简单）

```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./install-plugin.sh
```

脚本会：
1. 检测你的 IDE
2. 自动构建插件包
3. 安装到正确位置
4. 备份旧版本

**预期时间：** 2-3 分钟

---

### 🥈 推荐方案 2: 手动修复兼容性（推荐有经验用户）

#### 第一步：确定你的 IDE 版本
在你的 IDE 中：Help → About → 记下 Build 号（例如 241、243 等）

#### 第二步：更新 `build.gradle.kts`

```bash
# 使用编辑器打开文件
nano /Users/wxl/GolandProjects/switch2windsuf/build.gradle.kts

# 或用 VS Code
code /Users/wxl/GolandProjects/switch2windsuf/build.gradle.kts
```

找到第 18-39 行，更新如下：

```kotlin
// 第 18 行 - 更新 IDE 基础版本
- version.set("2022.3")
+ version.set("2024.1")  // 改成你的 IDE 版本

// 第 38 行 - 更新 sinceBuild
- sinceBuild.set("223")
+ sinceBuild.set("241")  // 改成你的 Build 号
```

**IDE 版本对照表：**
- 2022.3 → 223
- 2023.1 → 231
- 2024.1 → 241
- 2024.2 → 242
- 2024.3 → 243

#### 第三步：重新构建

```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./gradlew clean buildPlugin
```

#### 第四步：安装

```bash
./install-plugin.sh
```

---

## 📦 快速本地打包命令

### 方式 A: 使用已有构建（最快，仅 30 秒）

```bash
cd /Users/wxl/GolandProjects/switch2windsuf

# 查看已构建的插件包
ls -lh build/libs/switch2windsurf-2.0.0.jar

# 运行安装脚本
./install-plugin.sh
```

### 方式 B: 完整重新构建（推荐修改配置后）

```bash
cd /Users/wxl/GolandProjects/switch2windsuf

# 清理旧构建
./gradlew clean

# 构建插件包
./gradlew buildPlugin

# 构建完成后，输出在这里：
# ./build/libs/switch2windsurf-2.0.0.jar
# 或
# ./build/distributions/switch2windsurf-2.0.0.zip

# 安装
./install-plugin.sh
```

### 方式 C: 仅构建不安装

```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./gradlew buildPlugin
```

输出：`./build/distributions/switch2windsurf-2.0.0.zip`

---

## 🚀 完整安装流程（含验证）

### 步骤 1: 构建
```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./gradlew clean buildPlugin
```

### 步骤 2: 安装
```bash
./install-plugin.sh
# 按提示选择 IDE（GoLand？ IntelliJ？等）
```

### 步骤 3: 验证安装位置
```bash
# macOS 用户
ls -lh ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf/lib/

# 应该看到：
# switch2windsurf-2.0.0.jar
```

### 步骤 4: 重启 IDE
完全关闭 IDE，然后重新打开

### 步骤 5: 验证功能
在 IDE 中：
1. Settings/Preferences → Plugins
2. 搜索 "Switch2Windsurf"
3. 应该看到 **✅ 已启用** 的状态
4. 进入 Tools 菜单，应该看到新的菜单项

---

## 🎯 常见安装位置参考

### macOS
```
~/Library/Application Support/JetBrains/GoLand/plugins/Switch2Windsurf/lib/switch2windsurf-2.0.0.jar
~/Library/Application Support/JetBrains/IntelliJIdea/plugins/Switch2Windsurf/lib/switch2windsurf-2.0.0.jar
```

### Linux
```
~/.local/share/JetBrains/GoLand/plugins/Switch2Windsurf/lib/switch2windsurf-2.0.0.jar
~/.local/share/JetBrains/IntelliJIdea/plugins/Switch2Windsurf/lib/switch2windsurf-2.0.0.jar
```

### Windows
```
%APPDATA%\JetBrains\GoLand\plugins\Switch2Windsurf\lib\switch2windsurf-2.0.0.jar
%APPDATA%\JetBrains\IntelliJIdea\plugins\Switch2Windsurf\lib\switch2windsurf-2.0.0.jar
```

---

## 📋 当前构建状态

✅ **插件已构建！**

```
文件位置：./build/libs/switch2windsurf-2.0.0.jar
文件大小：4.0K
类型：JAR (Zip 压缩)
版本：2.0.0
IDE 基础版本：2022.3 (建议升级到 2024.1+)
```

---

## 🔍 如果安装后仍然显示不兼容

### 快速诊断

```bash
# 1. 查看 IDE 的 Build 号
echo "Check: Help → About → Build number in your IDE"

# 2. 检查插件构建时的配置
grep "sinceBuild\|version.set" /Users/wxl/GolandProjects/switch2windsuf/build.gradle.kts

# 3. 查看构建后的实际配置
cat /Users/wxl/GolandProjects/switch2windsuf/build/patchedPluginXmlFiles/plugin.xml | grep -A 5 "idea-plugin"

# 4. 查看 IDE 日志
tail -50 ~/Library/Logs/JetBrains/GoLand/system.log | grep -i incompatible
```

### 常见解决

如果上述诊断显示版本不匹配，重复"推荐方案 2"中的步骤。

---

## 🎓 文档导航

根据你的需求选择相应文档：

| 文档 | 用途 | 阅读时间 |
|-----|-----|--------|
| **QUICK_START.md** | 快速上手指南 | 5 分钟 |
| **LOCAL_BUILD_GUIDE.md** | 完整打包说明 | 15 分钟 |
| **TROUBLESHOOTING.md** | 故障排除详解 | 20 分钟 |
| 本文档 | 概览和汇总 | 10 分钟 |

---

## 🎮 安装后的使用方法

### 快捷键
- **Alt+Shift+O** - 在 Windsurf 中打开当前文件
- **Alt+Shift+P** - 在 Windsurf 中打开当前项目

### 菜单方式
- 编辑器右键 → Open File In Windsurf
- 项目视图右键 → Open Project In Windsurf
- Tools 菜单 → Switch2Windsurf 选项

### 配置
- Settings/Preferences → Tools → Switch2Windsurf
- 设置 Windsurf 可执行文件路径

---

## 💡 常见问题

**Q: 插件包这么小（4.0K）？**
A: 这是一个轻量级插件，只依赖 IDE 的标准平台库，不需要大的依赖。

**Q: 为什么有两个 JAR 文件？**
A: `switch2windsurf-2.0.0.jar` 是正式的，`instrumented-*.jar` 是 IDE 自动生成的测试版本，都可以使用。

**Q: 可以同时在多个 IDE 中安装吗？**
A: 可以！每个 IDE 有自己的插件目录，相互独立。

**Q: 如何卸载插件？**
A: 删除插件目录：
```bash
rm -rf ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf
# 然后重启 IDE
```

**Q: 如何升级插件版本？**
A: 重新运行 `./install-plugin.sh`，脚本会自动覆盖旧版本。

---

## 🚀 现在就开始吧！

```bash
# 最快的方式（建议）
cd /Users/wxl/GolandProjects/switch2windsuf
./install-plugin.sh
```

---

## 📞 需要帮助？

1. **快速开始** → 阅读 `QUICK_START.md`
2. **详细步骤** → 阅读 `LOCAL_BUILD_GUIDE.md`
3. **解决问题** → 阅读 `TROUBLESHOOTING.md`
4. **报告问题** → https://github.com/wxlbd/switch2windsurf/issues

---

**最后更新：** 2025-12-03
**版本：** 2.0.0
**作者：** wxlbd

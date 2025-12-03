# ✅ 本地打包检查清单

## 📋 为什么插件显示不兼容？

**根本原因：** 插件配置声明支持 IDE 2022.3+，但可能你在使用更新版本的 IDE。

### 具体问题位置
- `build.gradle.kts:18` - `version.set("2022.3")` ← 太低
- `build.gradle.kts:38` - `sinceBuild.set("223")` ← 太旧

---

## 🚀 三种解决方案

### ① 最快方案（仅需 30 秒）
```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./install-plugin.sh
# 选择你的 IDE，脚本会自动处理
```

### ② 推荐方案（解决根本问题）
```bash
# 1. 查看你的 IDE Build 号：Help → About → Build number
# 2. 编辑 build.gradle.kts，更新这两行：
#    version.set("2024.1")        (改成你的版本)
#    sinceBuild.set("241")        (改成对应的 Build 号)
# 3. 重新构建和安装：
./gradlew clean buildPlugin
./install-plugin.sh
```

### ③ 详细方案（含完整步骤）
阅读：`QUICK_START.md` 或 `LOCAL_BUILD_GUIDE.md`

---

## 📦 现在可以使用

✅ **插件已构建！** 位置：
```
./build/libs/switch2windsurf-2.0.0.jar (4.0 KB)
```

---

## 🎯 IDE 版本对照速查表

| 你的 IDE | Build 号 | 更新 synceBuild 为 |
|---------|---------|-----------------|
| 2022.3  | 223     | `sinceBuild.set("223")` |
| 2023.1  | 231     | `sinceBuild.set("231")` |
| 2023.2  | 232     | `sinceBuild.set("232")` |
| 2024.1  | 241     | `sinceBuild.set("241")` |
| 2024.2  | 242     | `sinceBuild.set("242")` |
| 2024.3  | 243     | `sinceBuild.set("243")` |

---

## 📂 文档文件说明

| 文件 | 说明 | 推荐阅读时间 |
|-----|------|-----------|
| **QUICK_START.md** | 3 步快速开始 | ⏱️ 5 分钟 |
| **LOCAL_BUILD_GUIDE.md** | 完整打包说明 | ⏱️ 15 分钟 |
| **TROUBLESHOOTING.md** | 问题诊断和修复 | ⏱️ 20 分钟 |
| **INSTALLATION_SUMMARY.md** | 全面总结和参考 | ⏱️ 10 分钟 |
| **install-plugin.sh** | 自动化安装脚本 | ✨ 推荐使用 |

---

## 🎓 我应该读哪个文档？

**情况 1：我想立即安装** → 执行 `./install-plugin.sh` ✨

**情况 2：我想理解如何打包** → 读 `QUICK_START.md`

**情况 3：我遇到了不兼容错误** → 读 `TROUBLESHOOTING.md`

**情况 4：我想要完整的参考信息** → 读 `INSTALLATION_SUMMARY.md`

---

## 🔍 快速诊断

运行这个命令看看当前配置：

```bash
cd /Users/wxl/GolandProjects/switch2windsuf

# 显示当前 IDE 版本配置
grep -n "version.set\|sinceBuild.set" build.gradle.kts

# 显示当前插件配置
cat src/main/resources/META-INF/plugin.xml | head -50
```

---

## 🎮 安装后怎么用？

安装成功后，你可以：

**快捷键：**
- `Alt+Shift+O` - 在 Windsurf 中打开当前文件
- `Alt+Shift+P` - 在 Windsurf 中打开当前项目

**菜单：**
- 编辑器右键菜单
- 项目视图右键菜单
- Tools 菜单

**配置：**
- Settings → Tools → Switch2Windsurf
- 设置 Windsurf 可执行文件路径

---

## ❓ 常见问题

**Q: 运行脚本后，IDE 还是显示不兼容？**
A: 这表示你的 IDE 版本与插件配置不匹配。阅读 `TROUBLESHOOTING.md` 的"步骤 1"进行诊断。

**Q: 插件包这么小（4KB），是不是不完整？**
A: 这是正常的！这个插件只依赖 IDE 的平台库，很轻量级。

**Q: 我可以同时在多个 IDE 中安装吗？**
A: 可以。每个 IDE 都有自己的插件目录，相互独立。

**Q: 如何卸载插件？**
A: 删除插件目录（macOS 为例）：
```bash
rm -rf ~/Library/Application\ Support/JetBrains/GoLand/plugins/Switch2Windsurf
# 重启 IDE
```

---

## 📞 需要帮助？

1. **看不懂英文？** 所有文档都有中文说明
2. **不知道从哪开始？** 直接运行 `./install-plugin.sh`
3. **解决不了问题？** 查看 `TROUBLESHOOTING.md`
4. **有 Bug 要报告？** https://github.com/wxlbd/switch2windsurf/issues

---

## ⚡ 一句话总结

当前插件配置太旧（2022.3），你的 IDE 版本更新（2024+），所以显示不兼容。**最简单的解决方案是运行 `./install-plugin.sh` 脚本，它会自动处理一切。**

---

**现在就开始吧！** 👇

```bash
cd /Users/wxl/GolandProjects/switch2windsuf
./install-plugin.sh
```

---

**版本：** 2.0.0
**最后更新：** 2025-12-03
**状态：** ✅ 准备就绪

# Switch2Windsurf

[English](README.md)

> 💡 推荐在 Windsurf 中配合 [Switch2IDEA](https://github.com/wxlbd/switch2idea) 使用


[![JetBrains Plugins](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID?label=JetBrains%20Marketplace&style=for-the-badge&logo=intellij-idea)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID?style=for-the-badge&logo=intellij-idea)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![License](https://img.shields.io/badge/license-MIT-blue.svg?style=for-the-badge)](LICENSE)

## 🔍 项目简介
一个提升开发效率的 JetBrains IDE 插件,让你在 JetBrains IDE 和 Windsurf 之间实现丝滑切换

![Switch2Windsurf演示](images/switch-show.gif)

## 🌟 功能特性

- 🚀 无缝编辑器切换
  - 在 JetBrains IDE 和 Windsurf 之间一键切换
  - 自动定位到相同的光标位置(行号和列号)
  - 完美保持编辑上下文,不中断思路

- ⌨️ 便捷的快捷键支持
  - macOS:
    - `Option+Shift+P` - 在 Windsurf 中打开整个项目
    - `Option+Shift+O` - 在 Windsurf 中打开当前文件
  - Windows:
    - `Alt+Shift+P` - 在 Windsurf 中打开整个项目
    - `Alt+Shift+O` - 在 Windsurf 中打开当前文件

- 🔧 多样化的访问方式
  - 快捷键操作
  - 编辑器右键菜单
  - IDE 工具菜单

## 🛠️ 安装指南

### 方式一:通过 JetBrains 插件市场安装
1. 打开 IDE → `Settings` → `Plugins` → `Marketplace`
2. 搜索 switch2windsurf
3. 点击 `Install` 完成安装
4. 点击 `OK` 生效

### 方式二:本地安装
1. 从 [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/PLUGIN_ID) 下载最新版插件包
2. IDE → `Settings` → `Plugins` → `⚙️`→ `Install Plugin from Disk...`
3. 选择下载的插件包
4. 点击 `OK` 生效


## 🚀 使用说明

### 基础使用

#### 打开项目
- 快捷键:
  - macOS: `Option+Shift+P`
  - Windows: `Alt+Shift+P`
- 右键菜单:在项目视图中右键 → `Open Project In Windsurf`
- 工具菜单:`Tools` → `Open Project In Windsurf`

#### 打开当前文件
- 快捷键:
  - macOS: `Option+Shift+O`
  - Windows: `Alt+Shift+O`
- 右键菜单:在编辑器中右键 → `Open File In Windsurf`
- 工具菜单:`Tools` → `Open File In Windsurf`

### 配置
- 在 `Settings/Preferences` → `Tools` → `Switch2Windsurf` 中:
  - 设置 Windsurf 可执行文件路径(默认为 "windsurf")
  - 通过 Keymap 设置自定义快捷键

### 环境要求
- 已安装 [Windsurf](https://codeium.com/windsurf)
- 兼容所有 JetBrains IDE(2022.3 及以上版本)

## 🧑‍💻 开发者指南

### 项目构建
```bash
# 克隆仓库
git clone https://github.com/wxlbd/switch2windsurf.git

# 构建插件
cd switch2windsurf
./gradlew buildPlugin
# 生成插件包在 build/distributions/ 目录下
```

### 贡献代码
1. Fork 本仓库
2. 创建特性分支
3. 提交修改
4. 推送分支
5. 提交 Pull Request

## 🙋 常见问题

### 1. 为什么安装之后快捷键/点击菜单不会跳转到 Windsurf?
检查是否在 `Settings` → `Tools` → `Switch2Windsurf` 中配置了正确的 Windsurf 可执行文件路径

### 2. 都支持哪些 IDE?
支持所有 JetBrains 系列的 IDE,如:IntelliJ IDEA、PyCharm、WebStorm、GoLand、RustRover、Android Studio 等

### 3. 都支持哪些版本?
插件基于 JDK 17 开发,目前仅支持 JetBrains IDE 2022.3 及以上版本

### 4. 如何修改插件的快捷键?
在 `Settings` → `Keymap` → `Plugins` → `Switch2Windsurf` 中修改

## 📄 许可证
本项目采用 [MIT License](LICENSE) 开源协议


## 📮 问题反馈
如果遇到问题或有建议,请通过以下方式反馈:
- [提交 GitHub Issue](https://github.com/wxlbd/switch2windsurf/issues)

## 🌟 Star 历史

[![Star History Chart](https://api.star-history.com/svg?repos=wxlbd/switch2windsurf&type=Date)](https://star-history.com/#wxlbd/switch2windsurf&Date)

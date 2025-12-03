#!/bin/bash

# Switch2Windsurf 本地安装脚本
# 用于快速安装插件到你的 JetBrains IDE

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PLUGIN_JAR="${PROJECT_DIR}/build/libs/switch2windsurf-2.0.0.jar"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}Switch2Windsurf 本地安装脚本${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

# 检查插件包是否存在
if [ ! -f "$PLUGIN_JAR" ]; then
    echo -e "${YELLOW}[步骤 1] 插件包不存在，开始构建...${NC}"
    cd "$PROJECT_DIR"
    ./gradlew clean buildPlugin
    echo -e "${GREEN}✓ 构建完成${NC}"
    echo ""
else
    echo -e "${GREEN}✓ 找到现有插件包${NC}"
    echo "  位置: $PLUGIN_JAR"
    echo ""
fi

# 检测 IDE 类型
echo -e "${YELLOW}[步骤 2] 检测你使用的 IDE...${NC}"
echo "请选择你使用的 IDE："
echo "1) IntelliJ IDEA"
echo "2) GoLand"
echo "3) PyCharm"
echo "4) WebStorm"
echo "5) Android Studio"
echo "6) 其他（手动输入 IDE 名称）"
echo ""
read -p "请输入选项 (1-6): " IDE_CHOICE

case $IDE_CHOICE in
    1) IDE_NAME="IntelliJ IDEA" ;;
    2) IDE_NAME="GoLand" ;;
    3) IDE_NAME="PyCharm" ;;
    4) IDE_NAME="WebStorm" ;;
    5) IDE_NAME="AndroidStudio" ;;
    6) read -p "请输入 IDE 名称 (如: IntelliJ IDEA, GoLand, PyCharm): " IDE_NAME ;;
    *) echo -e "${RED}✗ 无效选择${NC}"; exit 1 ;;
esac

echo -e "${GREEN}✓ 选择的 IDE: $IDE_NAME${NC}"
echo ""

# 检测操作系统和 IDE 插件目录
echo -e "${YELLOW}[步骤 3] 确定 IDE 插件目录...${NC}"

if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    PLUGINS_DIR="$HOME/Library/Application Support/JetBrains/$IDE_NAME/plugins"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    PLUGINS_DIR="$HOME/.local/share/JetBrains/$IDE_NAME/plugins"
elif [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "cygwin" ]]; then
    # Windows
    PLUGINS_DIR="$APPDATA/JetBrains/$IDE_NAME/plugins"
else
    echo -e "${RED}✗ 不支持的操作系统${NC}"
    exit 1
fi

echo "插件目录: $PLUGINS_DIR"
echo ""

# 创建插件目录
if [ ! -d "$PLUGINS_DIR" ]; then
    echo -e "${YELLOW}创建插件目录...${NC}"
    mkdir -p "$PLUGINS_DIR"
fi

# 创建插件子目录
PLUGIN_INSTALL_DIR="$PLUGINS_DIR/Switch2Windsurf/lib"
echo -e "${YELLOW}[步骤 4] 安装插件...${NC}"

if [ -d "$PLUGIN_INSTALL_DIR" ]; then
    echo -e "${YELLOW}发现现有插件，备份中...${NC}"
    BACKUP_DIR="${PLUGIN_INSTALL_DIR}.backup.$(date +%s)"
    mv "$PLUGIN_INSTALL_DIR" "$BACKUP_DIR"
    echo -e "${GREEN}✓ 备份到: $BACKUP_DIR${NC}"
fi

mkdir -p "$PLUGIN_INSTALL_DIR"
cp "$PLUGIN_JAR" "$PLUGIN_INSTALL_DIR/"

echo -e "${GREEN}✓ 插件已安装${NC}"
echo "  安装位置: $PLUGIN_INSTALL_DIR"
echo ""

# 完成
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}安装完成！${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "${YELLOW}后续步骤：${NC}"
echo "1. 完全关闭你的 JetBrains IDE"
echo "2. 重新打开 IDE"
echo "3. 进入 Settings/Preferences → Plugins"
echo "4. 搜索 'Switch2Windsurf' 验证安装"
echo ""
echo -e "${YELLOW}快捷键：${NC}"
echo "  • Alt+Shift+O - 在 Windsurf 中打开当前文件"
echo "  • Alt+Shift+P - 在 Windsurf 中打开当前项目"
echo ""
echo -e "${YELLOW}需要帮助？${NC}"
echo "  查看完整指南: $PROJECT_DIR/LOCAL_BUILD_GUIDE.md"
echo ""

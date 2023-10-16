#!/bin/bash

# 服务太多时，在统一 bin 目录下的 sh 文件，以通配符的方式，批量重启或停止
# 启动示例：
# ./imall-batch.sh restart 'imall-notice-*'
# ./imall-batch.sh status 'imall-notice-client-01.sh'

# 定义一个函数来执行操作（restart 或 status）并传递文件名
execute_operation() {
    local file="$1"
    local operation="$2"
    
    if [ -f "$file" ] && [ "$file" != "${0##*/}" ]; then
        ./"$file" "$operation"
    fi
}

operation="$1" #（restart 或 status）
file_pattern="$2" # 文件名

if [ -z "$operation" ] || [ -z "$file_pattern" ]; then
    echo "用法: ./$0 <操作> <文件名模式>"
    echo "例如： ./$0 restart imall-notice-*"
    exit 1
fi

# 检查是否有匹配的文件
shopt -s nullglob
files=($file_pattern)

if [ ${#files[@]} -eq 0 ]; then
  echo "没有找到匹配的文件: $file_pattern"
else
  for file in "${files[@]}"; do
    execute_operation "$file" "$operation"
    sleep 1
    echo
  done
fi

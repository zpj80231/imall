package com.imall.thirdparty.common;

/**
 * 动态表名插件
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
public class DynamicTableNamePlugin {

    private static ThreadLocal<String> table = new ThreadLocal<>();

    public static void setTable(String tableName) {
        table.set(tableName);
    }

    public static String getTable() {
        return table.get();
    }

    public static void remove() {
        table.remove();
    }
}

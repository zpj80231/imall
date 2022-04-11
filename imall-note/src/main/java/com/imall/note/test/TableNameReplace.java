package com.imall.note.test;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname TableNameReplace
 * @Description 对所有符合格式的sql文件，批量修改表名
 * @Date 2020-04-30
 * @author zpj
 */
public class TableNameReplace {

    /**
     * 文件或文件夹路径
     */
    private static String filePath = "C:\\Users\\admin\\Desktop\\test";
    /**
     * fileExt: 要找的文件后缀
     * eventFileContainsName: 告警文件名称至少包含
     * systemFileContainsName: 系统文件名称至少包含
     * interceptIndex: 截取下标
     * eventTableName: 告警表替换后的文件内容
     * systemTableName: 系统表替换后的文件内容
     */
    private static String fileExt = ".sql";
    private static String eventFileContainsName = "告警";
    private static String systemFileContainsName = "系统";
    private static String interceptIndex = "VALUES";
    private static String eventTableName = "INSERT INTO `hq_event_warning_yx` (`provinceId`, `warningYxId`, `warningId`, `warningSubType`, `warningTime`, `handleTime`, `warningLevel`, `attackTarget`, `attackSrc`, `warningStatus`, `network`, `warningCount`, `SystemId`, `OperCorp`, `VALUE1`, `VALUE2`, `VALUE3`, `hostName`, `hostType`, `hostOsType`, `hostOsVersion`, `updateTime`, `lastWarningTime`, `firstWarningTime`, `WarningCode`, `warningSrc`) ";
    private static String systemTableName = "INSERT INTO `hq_appsystem_yx` (`provinceId`, `systemId`, `name`, `nickName`, `hostNum`, `currentStatus`, `hasTopo`, `spreadType`, `deployType`, `operCorp`, `securityLevel`, `securityDomain`, `networkCode`, `network`, `updateTime`) ";
    /**
     * 计数器
     */
    private static HashMap<String, Integer> counter = new HashMap<>();
    private static final String ALARM_FILE_COUNT_KEY = "告警文件";
    private static final String SYSTEM_FILE_COUNT_KEY = "系统文件";
    private static final String ERROR_FILE_COUNT_KEY = "错误文件";

    public static void main(String[] args) throws IOException {

//        String jarPath = TableNameReplace.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        jarPath = jarPath.replaceAll("/","\\\\").substring(1);
//        Runtime.getRuntime().exec("cmd /k start cmd.exe /k java -jar "+jarPath);
//        System.err.println(jarPath);

        File resource = new File(filePath);
        if (resource.exists() && resource.isFile()) {
            checkOrHandle(resource, true);
        }

        //先检查一遍文件格式待文件格式全部正确后处理文件
        if (resource.exists() && resource.isDirectory()) {
            File[] rs = resource.listFiles();
            for (File r : rs) {
                checkOrHandle(r, true);
            }

            System.out.println(" --------------------------------------- ");
            for (Map.Entry<String, Integer> entity : counter.entrySet()) {
                System.out.println("|\t\t找到" + entity.getKey() + "：  " + entity.getValue() + " 个\t\t\t\t|");
            }
            System.out.println(" --------------------------------------- ");
        }

        if (counter.containsKey(ERROR_FILE_COUNT_KEY)) {
            System.err.println("\n=====   请修改错误文件格式,然后再次运行  =====");
            return;
        }

        if (counter.size() < 1) {
            System.err.println("\n--> 无对应文件，无需处理...");
            return;
        }

        System.out.println("\n--> 经检查文件格式全部正确, 开始处理请稍候...");

        if (resource.exists() && resource.isFile()) {
            checkOrHandle(resource, false);
        }
        if (resource.exists() && resource.isDirectory()) {
            File[] rs = resource.listFiles();
            for (File r : rs) {
                checkOrHandle(r, false);
            }
        }

        System.out.println("\n--> 恭喜, 文件处理成功, 请自行查看!");

    }

    /**
     * 检查格式或处理文件
     *
     * @param tar	文件
     * @param isCheck	是否检查
     * @return void
     */
    private static void checkOrHandle(File tar, boolean isCheck) {
        File[] ds = tar.listFiles(x -> x.isDirectory());
        File[] js = tar.listFiles(x -> x.isFile() && x.getName().toLowerCase().endsWith(fileExt));
        if (tar.isFile() && tar.getName().toLowerCase().endsWith(fileExt)) {
            mark(tar);
        }
        if (ds != null) {
            Arrays.stream(ds).forEach(x -> {
                checkOrHandle(x, isCheck);
            });
        }
        if (js != null) {
            Arrays.stream(js).forEach(x -> {
                if(isCheck) {
                    mark(x);
                } else {
                    if(x.getName().contains(eventFileContainsName)) {
                        repalceTableName(x,eventTableName);
                    }
                    if(x.getName().contains(systemFileContainsName)) {
                        repalceTableName(x,systemTableName);
                    }
                }
            });
        }
    }

    /**
     * 对文件进行统计
     *
     * @param file	待统计的文件
     * @return void
     */
    private static void mark(File file) {
        //如果不是指定格式的文件名称，则打印提示
        //统计告警和系统的文件数量
        if (file.getName().contains(eventFileContainsName)) {
            counter.merge(ALARM_FILE_COUNT_KEY, 1, Integer::sum);
        } else if (file.getName().contains(systemFileContainsName)) {
            counter.merge(SYSTEM_FILE_COUNT_KEY, 1, Integer::sum);
        } else {
            counter.merge(ERROR_FILE_COUNT_KEY, 1, Integer::sum);
            try {
                System.err.println("请查看文件名称是否正确：" + file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 表名替换
     *
     * @param file 要替换的文件
     * @param replaceStr 替换的字符串
     * @return void
     */
    private static void repalceTableName(File file, String replaceStr) {
        FileReader in;
        try {
            in = new FileReader(file);
            BufferedReader bufIn = new BufferedReader(in);
            CharArrayWriter tempStream = new CharArrayWriter();

            String line;
            while ((line = bufIn.readLine()) != null) {
                line = replaceStr + line.substring(line.indexOf(interceptIndex));
                tempStream.write(line);
                tempStream.append(System.getProperty("line.separator"));
            }
            bufIn.close();

            FileWriter out = new FileWriter(file);
            tempStream.writeTo(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

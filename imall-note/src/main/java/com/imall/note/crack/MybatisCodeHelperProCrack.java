package com.imall.note.crack;

import javassist.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author zhangpengjun
 * @date 2023/6/26
 * @see <a href="https://zpj80231.github.io/znote/views/crack/mybatiscodehelperprocrack.html">MyBatisCodeHelperProCrack Documentation</a>
 */
@Slf4j
public class MybatisCodeHelperProCrack {

    private static final String HOME = System.getProperty("user.home");
    public static final String WRITE_CLASS_FILE_DIRECTORY_NAME = HOME + "/crack/MyBatisCodeHelper-Pro";
    private static final String GSON_JAR_PATH = HOME + "/.m2/repository/com/google/code/gson/gson/2.9.1/gson-2.9.1.jar";
    /**
     * origin jar
     * win: C:\Users\{user}\AppData\Roaming\JetBrains\IntelliJIdea2023.2\plugins\MyBatisCodeHelper-Pro\lib
     * mac: ~/Library/Application\ Support/JetBrains/IntelliJIdea2024.1/plugins/MyBatisCodeHelper-Pro/lib
     */
    private static String myBatisCodeHelperProObfussJarPath = WRITE_CLASS_FILE_DIRECTORY_NAME + "/MyBatisCodeHelper-Pro-obfuss.jar";

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        log.info("==== MyBatisCodeHelper-Pro crack start ====");
        crack341_2321();
        log.info("==== MyBatisCodeHelper-Pro crack end ====");
    }

    /**
     * MyBatisCodeHelper-Pro 3.4.1+2321 crack
     */
    private static void crack341_2321() throws NotFoundException, CannotCompileException, IOException {
        myBatisCodeHelperProObfussJarPath = WRITE_CLASS_FILE_DIRECTORY_NAME + "/instrumented-MyBatisCodeHelper-Pro241-3.4.1+2321.jar";
        String validToClassPath = "com.ccnode.codegenerator.af.d.f";
        String gsonClassPath = "com.ccnode.codegenerator.af.f.e";
        String gsonClassPathMethodName = "a";
        crack(validToClassPath, gsonClassPath, gsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.3.0 crack
     */
    private static void crack330() throws NotFoundException, CannotCompileException, IOException {
        String validToClassPath = "com.ccnode.codegenerator.validate.response.ValidateNewResultData";
        String gsonClassPath = "com.ccnode.codegenerator.validate.utils.RsaUtils";
        String gsonClassPathMethodName = "fromEncrptData";
        crack(validToClassPath, gsonClassPath, gsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.7 crack
     */
    private static void crack327() throws NotFoundException, CannotCompileException, IOException {
        String validToClassPath = "com.ccnode.codegenerator.J.d.f";
        String gsonClassPath = "com.ccnode.codegenerator.J.e.e";
        String gsonClassPathMethodName = "a";
        crack(validToClassPath, gsonClassPath, gsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.2 crack
     */
    private static void crack322() throws NotFoundException, CannotCompileException, IOException {
        String validToClassPath = "com.ccnode.codegenerator.w.d.c";
        String gsonClassPath = "com.ccnode.codegenerator.w.f.a";
        String gsonClassPathMethodName = "a";
        crack(validToClassPath, gsonClassPath, gsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.1 crack
     */
    private static void crack321() throws NotFoundException, CannotCompileException, IOException {
        String validToClassPath = "com.ccnode.codegenerator.T.e.b";
        String gsonClassPath = "com.ccnode.codegenerator.T.c.a";
        String gsonClassPathMethodName = "a";
        crack(validToClassPath, gsonClassPath, gsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.0 crack
     */
    private static void crack320() throws NotFoundException, CannotCompileException, IOException {
        String validToClassPath = "com.ccnode.codegenerator.T.a.b";
        String gsonClassPath = "com.ccnode.codegenerator.T.e.c";
        String gsonClassPathMethodName = "a";
        crack(validToClassPath, gsonClassPath, gsonClassPathMethodName);
    }

    private static void crack(String validToClassPath, String gsonClassPath, String gsonClassPathMethodName)
            throws NotFoundException, CannotCompileException, IOException {
        // 校验 jar 文件是否存在
        File jarFile = new File(myBatisCodeHelperProObfussJarPath);
        if (!jarFile.exists()) {
            throw new IOException("Jar 文件不存在: " + jarFile.getAbsolutePath());
        }
        log.info("加载目标 jar：{}", jarFile.getAbsolutePath());

        // 加载类，直接使用 jar 路径
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(myBatisCodeHelperProObfussJarPath);
        classPool.appendClassPath(GSON_JAR_PATH);

        // 获取目标类及方法
        CtClass ctClass = classPool.get(gsonClassPath);
        CtMethod parseLicenseMethod = ctClass.getDeclaredMethod(gsonClassPathMethodName, new CtClass[]{classPool.get("java.lang.String")});
        String body = "{" +
                "\tcom.google.gson.Gson gson = new com.google.gson.Gson();" +
                "\t" + validToClassPath + " e = (" + validToClassPath + ")gson.fromJson($1," + validToClassPath + ".class);" +
                "\treturn e;" +
                "}";
        parseLicenseMethod.setBody(body);

        // 对修改的文件，写出到一个新文件
        ctClass.writeFile(WRITE_CLASS_FILE_DIRECTORY_NAME);
        log.info("目标 class：{} 修改成功，已输出至目录：{}", gsonClassPath, WRITE_CLASS_FILE_DIRECTORY_NAME);

        // 调用封装方法更新 jar 包
        updateJarWithModifiedClass(myBatisCodeHelperProObfussJarPath, WRITE_CLASS_FILE_DIRECTORY_NAME, gsonClassPath);
    }

    @SneakyThrows
    private static void updateJarWithModifiedClass(String jarPath, String baseOutputDir, String targetClass) {
        // 计算 class 文件路径（相对于输出目录）
        String classFilePath = targetClass.replace('.', '/') + ".class";
        File compiledClassFile = new File(Paths.get(baseOutputDir, classFilePath).toString());
        if (!compiledClassFile.exists()) {
            throw new IOException("生成的 class 文件不存在：" + compiledClassFile.getAbsolutePath());
        }

        // 执行 jar 命令将 class 文件更新回 jar 包
        ProcessBuilder pb = new ProcessBuilder(
                "jar", "uvf",
                jarPath,
                classFilePath
        );
        pb.directory(new File(baseOutputDir));
        pb.inheritIO();

        log.info("目标 class：{}, 更新回 jar 包：{} \njar uvf {} {}", targetClass, jarPath, jarPath, classFilePath);

        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            log.info("更新成功。目标 jar:{}, 目标 class：{}", jarPath, classFilePath);
        } else {
            throw new RuntimeException("jar 更新失败，退出码：" + exitCode);
        }
    }


}

package com.imall.note.crack;

import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhangpengjun
 * @date 2023/6/26
 * @see <a href="https://zpj80231.github.io/znote/views/crack/mybatiscodehelperprocrack.html">MyBatisCodeHelperProCrack Documentation</a>
 */
@Slf4j
public class MybatisCodeHelperProCrack {

    public static final String WRITE_CLASS_FILE_DIRECTORY_NAME = "/Users/zhangpj/crack/MyBatisCodeHelper-Pro";
    private static final String GSON_JAR_PATH = "/Users/zhangpj/.m2/repository/com/google/code/gson/gson/2.9.1/gson-2.9.1.jar";
    /**
     * origin jar
     * win: C:\Users\{user}\AppData\Roaming\JetBrains\IntelliJIdea2023.2\plugins\MyBatisCodeHelper-Pro\lib
     * mac: ~/Library/Application\ Support/JetBrains/IntelliJIdea2024.1/plugins/MyBatisCodeHelper-Pro/lib
     */
    private static String myBatisCodeHelperProObfussJarPath = WRITE_CLASS_FILE_DIRECTORY_NAME + "/MyBatisCodeHelper-Pro-obfuss.jar";

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        log.info("MyBatisCodeHelper-Pro crack start");
        crack341_2321();
        log.info("MyBatisCodeHelper-Pro crack end");
    }

    /**
     * MyBatisCodeHelper-Pro 3.4.1+2321 crack
     */
    private static void crack341_2321() throws NotFoundException, CannotCompileException, IOException {
        myBatisCodeHelperProObfussJarPath = WRITE_CLASS_FILE_DIRECTORY_NAME + "/instrumented-MyBatisCodeHelper-Pro241-3.4.1+2321.jar";
        String findValidToClassPath = "com.ccnode.codegenerator.af.d.f";
        String findGsonClassPath = "com.ccnode.codegenerator.af.f.e";
        String findGsonClassPathMethodName = "a";
        crack(findValidToClassPath, findGsonClassPath, findGsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.3.0 crack
     */
    private static void crack330() throws NotFoundException, CannotCompileException, IOException {
        String findValidToClassPath = "com.ccnode.codegenerator.validate.response.ValidateNewResultData";
        String findGsonClassPath = "com.ccnode.codegenerator.validate.utils.RsaUtils";
        String findGsonClassPathMethodName = "fromEncrptData";
        crack(findValidToClassPath, findGsonClassPath, findGsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.7 crack
     */
    private static void crack327() throws NotFoundException, CannotCompileException, IOException {
        String findValidToClassPath = "com.ccnode.codegenerator.J.d.f";
        String findGsonClassPath = "com.ccnode.codegenerator.J.e.e";
        String findGsonClassPathMethodName = "a";
        crack(findValidToClassPath, findGsonClassPath, findGsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.2 crack
     */
    private static void crack322() throws NotFoundException, CannotCompileException, IOException {
        String findValidToClassPath = "com.ccnode.codegenerator.w.d.c";
        String findGsonClassPath = "com.ccnode.codegenerator.w.f.a";
        String findGsonClassPathMethodName = "a";
        crack(findValidToClassPath, findGsonClassPath, findGsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.1 crack
     */
    private static void crack321() throws NotFoundException, CannotCompileException, IOException {
        String findValidToClassPath = "com.ccnode.codegenerator.T.e.b";
        String findGsonClassPath = "com.ccnode.codegenerator.T.c.a";
        String findGsonClassPathMethodName = "a";
        crack(findValidToClassPath, findGsonClassPath, findGsonClassPathMethodName);
    }

    /**
     * MyBatisCodeHelper-Pro 3.2.0 crack
     */
    private static void crack320() throws NotFoundException, CannotCompileException, IOException {
        String findValidToClassPath = "com.ccnode.codegenerator.T.a.b";
        String findGsonClassPath = "com.ccnode.codegenerator.T.e.c";
        String findGsonClassPathMethodName = "a";
        crack(findValidToClassPath, findGsonClassPath, findGsonClassPathMethodName);
    }

    private static void crack(String findValidToClassPath, String findGsonClassPath, String findGsonClassPathMethodName)
            throws NotFoundException, CannotCompileException, IOException {
        // 加载类
        ClassPool classPool = ClassPool.getDefault();
        // 直接使用 jar 路径
        classPool.appendClassPath(myBatisCodeHelperProObfussJarPath);
        classPool.appendClassPath(GSON_JAR_PATH);

        // 获取指定类
        CtClass ctClass = classPool.get(findGsonClassPath);
        CtMethod parseLicenseMethod = ctClass.getDeclaredMethod(findGsonClassPathMethodName, new CtClass[]{classPool.get("java.lang.String")});
        String body = "{" +
                "\tcom.google.gson.Gson gson = new com.google.gson.Gson();" +
                "\t" + findValidToClassPath + " e = (" + findValidToClassPath + ")gson.fromJson($1," + findValidToClassPath + ".class);" +
                "\treturn e;" +
                "}";
        parseLicenseMethod.setBody(body);

        // 对修改的文件，写出到一个新文件
        ctClass.writeFile(WRITE_CLASS_FILE_DIRECTORY_NAME);
    }

}

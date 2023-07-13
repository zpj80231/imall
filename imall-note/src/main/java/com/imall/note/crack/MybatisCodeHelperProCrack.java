package com.imall.note.crack;

import javassist.*;

import java.io.IOException;

/**
 * @author zhangpengjun
 * @date 2023/6/26
 */
public class MybatisCodeHelperProCrack {

    private static final String MY_BATIS_CODE_HELPER_PRO_OBFUSS_JAR_PATH = "D:\\crack\\MyBatisCodeHelper-Pro-obfuss.jar";
    private static final String GSON_2_10_1_JAR_PATH = "D:\\maven\\repositroy\\com\\google\\code\\gson\\gson\\2.10.1\\gson-2.10.1.jar";

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        crack321();
    }

    private static void crack321() throws NotFoundException, CannotCompileException, IOException {
        String domainObjectClassPathFromMbp = "com.ccnode.codegenerator.T.e.b";
        String gsonClassPathFromMbp = "com.ccnode.codegenerator.T.c.a";
        String gsonClassMethodNameFromMbp = "a";
        crack(domainObjectClassPathFromMbp, gsonClassPathFromMbp, gsonClassMethodNameFromMbp);
    }

    private static void crack320() throws NotFoundException, CannotCompileException, IOException {
        String modelClassPathFromMbp = "com.ccnode.codegenerator.T.a.b";
        String gsonClassPathFromMbp = "com.ccnode.codegenerator.T.e.c";
        String gsonClassMethodNameFromMbp = "a";
        crack(modelClassPathFromMbp, gsonClassPathFromMbp, gsonClassMethodNameFromMbp);
    }

    private static void crack(String modelClassPathFromMbp, String gsonClassPathFromMbp, String gsonClassMethodNameFromMbp)
            throws NotFoundException, CannotCompileException, IOException {
        // 加载类
        ClassPool classPool = ClassPool.getDefault();
        // 直接使用 jar 路径
        classPool.appendClassPath(MY_BATIS_CODE_HELPER_PRO_OBFUSS_JAR_PATH);
        classPool.appendClassPath(GSON_2_10_1_JAR_PATH);

        // 获取指定类
        CtClass ctClass = classPool.get(gsonClassPathFromMbp);
        CtMethod parseLicenseMethod = ctClass.getDeclaredMethod(gsonClassMethodNameFromMbp, new CtClass[]{classPool.get("java.lang.String")});
        String body = "{" +
                "\tcom.google.gson.Gson gson = new com.google.gson.Gson();" +
                "\t" + modelClassPathFromMbp + " e = (" + modelClassPathFromMbp + ")gson.fromJson($1," + modelClassPathFromMbp + ".class);" +
                "\treturn e;" +
                "}";
        parseLicenseMethod.setBody(body);

        // 对修改的文件，写出到一个新文件
        ctClass.writeFile("D:\\crack");
    }


}

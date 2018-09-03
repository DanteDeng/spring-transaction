package com.nature.ioc.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包工具类
 */
public class PackageUtil {

    private static final String TYPE_FILE = "file";

    private static final String TYPE_JAR = "jar";

    private static final String SUFFIX_CLASS = ".class";

    private static final String STRING_CLASSES = File.separator + "classes" + File.separator;

    /**
     * 获取某包下（包括该包的所有子包）所有类
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName) {
        return getClassName(packageName, true);
    }

    /**
     * 获取某包下所有类
     * @param packageName  包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName, boolean childPackage) {
        List<String> fileNames = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        Enumeration<URL> urls;
        try {
            urls = loader.getResources(packagePath);    //获取类加载器扫描的全部路径

            while (urls.hasMoreElements()) {    // 遍历全部满足条件的资源路径
                URL url = urls.nextElement();
                if (url != null) {
                    String type = url.getProtocol(); //获取路径类型
                    if (type.equals(TYPE_FILE)) {   // 文件目录
                        fileNames.addAll(getClassNameByFile(url.getPath(), childPackage));
                    } else if (type.equals(TYPE_JAR)) { //jar包
                        fileNames.addAll(getClassNameByJar(url.getPath(), childPackage));
                    }
                }
            }
            fileNames.addAll(getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     * @param filePath     文件路径
     *                     类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    if (childPackage) { // 查找子目录
                        myClassName.addAll(getClassNameByFile(childFile.getPath(), true));
                    }
                } else {
                    String childFilePath = childFile.getPath();
                    if (childFilePath.endsWith(SUFFIX_CLASS)) { //如果是class文件
                        childFilePath = childFilePath.substring(childFilePath.indexOf(STRING_CLASSES) + 9, childFilePath.lastIndexOf("."));
                        String className = childFilePath.replace(File.separator, ".");
                        myClassName.add(className);
                    }
                }
            }
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        if (urls != null) {
            for (URL url : urls) {
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jarFile != null) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        }
        return myClassName;
    }
}

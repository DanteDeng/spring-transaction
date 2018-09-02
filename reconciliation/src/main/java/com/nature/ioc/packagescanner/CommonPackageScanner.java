package com.nature.ioc.packagescanner;

import com.nature.ioc.definitions.PackageScanner;
import com.nature.ioc.util.PackageUtil;

import java.util.List;

/**
 * 通用包扫描器
 */
public class CommonPackageScanner implements PackageScanner {

    /**
     * 扫描指定包下类到类加载器
     * @param classLoader 类加载器
     * @param basePackage 包路径
     */
    @Override
    public void scanClassIntoClassLoader(ClassLoader classLoader, String basePackage) {
        List<String> classNames = PackageUtil.getClassName(basePackage);

        for (String className : classNames) {
            className = className.substring(className.indexOf(basePackage));
            try {
                classLoader.loadClass(className);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

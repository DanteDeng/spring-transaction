package com.nature.ioc.definitions;

/**
 * 包扫描器
 */
public interface PackageScanner {

    /**
     * 扫描指定包下类到类加载器
     * @param classLoader 类加载器
     * @param basePackage 包路径
     */
    void scanClassIntoClassLoader(ClassLoader classLoader, String basePackage);
}

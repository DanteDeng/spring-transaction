package com.nature.ioc.definitions;

/**
 * 上下文
 */
public interface Context extends BeanFactory {
    /**
     * 获取bean工厂
     * @return bean工厂
     */
    BeanFactory getBeanFactory();

    /**
     * 获取类加载器
     * @return 类加载器
     */
    ClassLoader getClassLoader();

    /**
     * 设置类加载器
     * @param classLoader 类加载器
     */
    void setClassLoader(ClassLoader classLoader);
}

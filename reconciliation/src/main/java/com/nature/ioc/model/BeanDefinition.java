package com.nature.ioc.model;

/**
 * 类定义
 */
public class BeanDefinition {

    /**
     * 类
     */
    private Class<?> defClass;
    /**
     * 实例
     */
    private Object bean;

    public Class<?> getDefClass() {
        return defClass;
    }

    public void setDefClass(Class<?> aClass) {
        this.defClass = aClass;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}

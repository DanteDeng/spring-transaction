package com.nature.ioc.definitions;

import com.nature.ioc.model.BeanDefinition;

import java.util.Map;
import java.util.Set;

/**
 * bean工厂
 */
public interface BeanFactory {

    /**
     * 根据ID获取bean
     * @param id bean的唯一标识
     * @return bean
     */
    Object getBean(String id);

    /**
     * 根据class获取bean
     * @param aClass class
     * @param <T>    类型
     * @return bean
     */
    <T> T getBean(Class<T> aClass);

    /**
     * 根据id以及class获取bean
     * @param id     bean的唯一标识
     * @param aClass class
     * @param <T>    类型
     * @return bean
     */
    <T> T getBean(String id, Class<T> aClass);

    /**
     * 获取工厂中缓存的全部bean
     * @return beansMap
     */
    Map<String, Object> getBeansMap();

    /**
     * 获取工厂中缓存的全部class-bean集合
     * @return beansSet
     */
    Set<BeanDefinition> getBeansSet();
}

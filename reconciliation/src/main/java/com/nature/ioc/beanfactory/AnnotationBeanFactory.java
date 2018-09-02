package com.nature.ioc.beanfactory;

import com.nature.ioc.definitions.BeanFactory;
import com.nature.ioc.model.BeanDefinition;

import java.util.Map;
import java.util.Set;

public class AnnotationBeanFactory implements BeanFactory {

    private Map<String, Object> beansMap;

    private Set<BeanDefinition> beansSet;

    public AnnotationBeanFactory(Map<String, Object> beansMap, Set<BeanDefinition> beansSet) {
        this.beansMap = beansMap;
        this.beansSet = beansSet;
    }

    @Override
    public Object getBean(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("param is empty");
        }
        Object bean = beansMap.get(id);
        if (bean == null) {
            throw new RuntimeException("no bean found");
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(Class<T> aClass) {
        if (aClass == null) {
            throw new RuntimeException("param is empty");
        }
        Object bean = null;
        for (BeanDefinition beanDefinition : beansSet) {
            if (bean == null) {
                if (aClass.equals(beanDefinition.getDefClass())) {
                    bean = beanDefinition.getBean();
                }
            } else {
                if (aClass.equals(beanDefinition.getDefClass())) {
                    throw new RuntimeException("more than one bean found");
                }
            }

        }
        if (bean == null) {
            throw new RuntimeException("no bean found");
        }

        return (T) bean;
    }

    @Override
    public <T> T getBean(String id, Class<T> aClass) {
        T bean = getBean(aClass);
        Object o = getBean(id);
        if (bean.equals(o)) {
            return bean;
        } else {
            throw new RuntimeException("no bean found");
        }
    }

    @Override
    public Map<String, Object> getBeansMap() {
        return beansMap;
    }

    @Override
    public Set<BeanDefinition> getBeansSet() {
        return beansSet;
    }
}

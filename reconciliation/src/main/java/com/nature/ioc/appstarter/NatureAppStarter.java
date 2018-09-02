package com.nature.ioc.appstarter;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.ioc.annotations.InjectBean;
import com.nature.ioc.context.AnnotationContext;
import com.nature.ioc.definitions.AppStarter;
import com.nature.ioc.definitions.BeanFactory;
import com.nature.ioc.definitions.Context;
import com.nature.ioc.model.BeanDefinition;
import com.nature.ioc.packagescanner.CommonPackageScanner;

import java.lang.reflect.Field;
import java.util.*;

public class NatureAppStarter implements AppStarter {

    private Context context = new AnnotationContext();

    @Override
    public void start() {

        BeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> beansMap = beanFactory.getBeansMap();
        Set<BeanDefinition> beansSet = beanFactory.getBeansSet();
        preGenBeans(context, beansMap, beansSet); // 初步生成beans集合
        postGenBeans(beanFactory, beansMap); // bean加工
    }

    private void postGenBeans(BeanFactory beanFactory, Map<String, Object> beansMap) {
        for (Map.Entry<String, Object> entry : beansMap.entrySet()) {
            Object bean = entry.getValue();
            Class<?> aClass = bean.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                InjectBean annotation = field.getAnnotation(InjectBean.class);
                if (annotation != null) {
                    String id = annotation.id();
                    Object fieldBean;
                    if (id.isEmpty()) {
                        Class<?> fieldClass = field.getType();
                        fieldBean = beanFactory.getBean(fieldClass);
                    } else {
                        fieldBean = beanFactory.getBean(id);
                    }
                    if (fieldBean == null) {
                        throw new RuntimeException("bean not found");
                    }
                    try {
                        field.set(bean, fieldBean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("inject filed error");
                    }
                }
            }
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    /**
     * 初步生成beans集合
     * @param context 上下文
     */
    private void preGenBeans(Context context, Map<String, Object> beans, Set<BeanDefinition> beansSet) {
        ClassLoader classLoader = context.getClassLoader();
        new CommonPackageScanner().scanClassIntoClassLoader(classLoader, "com.nature");
        try {
            Field field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Vector<Class<?>> classes = (Vector<Class<?>>) field.get(classLoader);
            List<Class<?>> list = new ArrayList<>(classes);
            for (Class<?> aClass : list) {
                if (aClass.getName().startsWith("com.nature")) {
                    ContextComponent contextComponent = aClass.getAnnotation(ContextComponent.class);
                    if (contextComponent != null) {
                        String id = contextComponent.id();
                        if (id.isEmpty()) {
                            id = aClass.getName();
                        }
                        if (beans.containsKey(id)) {
                            throw new RuntimeException("bean id is duplicated");
                        } else {
                            Object instance = aClass.newInstance();
                            beans.put(id, instance); // id bean 的map
                            genBeansSet(beansSet, aClass, instance); //class bean的set
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成bean的set
     * @param beansSet bean的set
     * @param aClass   class
     * @param instance bean实例
     */
    private void genBeansSet(Set<BeanDefinition> beansSet, Class<?> aClass, Object instance) {
        BeanDefinition classDefinition = new BeanDefinition();
        classDefinition.setDefClass(aClass);
        classDefinition.setBean(instance);
        beansSet.add(classDefinition);
        Class<?> superclass = aClass.getSuperclass();
        if (superclass != null) {
            BeanDefinition auperDefinition = new BeanDefinition();
            auperDefinition.setDefClass(superclass);
            auperDefinition.setBean(instance);
            beansSet.add(auperDefinition);
        }
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> bClass : interfaces) {
            genBeansSet(beansSet, bClass, instance);
        }

    }
}

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

/**
 * 容器启动器
 */
public class NatureAppStarter implements AppStarter {

    /**
     * 容器
     */
    private Context context = new AnnotationContext();

    /**
     * 启动
     */
    @Override
    public void start() {
        String basePackage = "com.nature";

        preGenBeans(context, basePackage);   // 生成beans预处理
        genBeans(context); // 生成beans集合
        postGenBeans(context); // beans加工
    }

    /**
     * 生成beans前置处理
     * @param context     上下文
     * @param basePackage 扫描包路径
     */
    private void preGenBeans(Context context, String basePackage) {
        ClassLoader classLoader = context.getClassLoader();
        new CommonPackageScanner().scanClassIntoClassLoader(classLoader, basePackage);
    }

    /**
     * 生成beans集合
     * @param context 上下文
     */
    private void genBeans(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        BeanFactory beanFactory = context.getBeanFactory(); // bean工厂
        Map<String, Object> beansMap = beanFactory.getBeansMap(); // beans map 集合
        Set<BeanDefinition> beansSet = beanFactory.getBeansSet(); // beans set 集合，存放class与bean实例关系集合（beanDefinition）
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
                        if (beansMap.containsKey(id)) {
                            throw new RuntimeException("bean id is duplicated");
                        } else {
                            Object instance = aClass.newInstance();
                            beansMap.put(id, instance); // id bean 的map
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

    /**
     * 生成beans后置处理
     * @param context 上下文
     */
    private void postGenBeans(Context context) {
        BeanFactory beanFactory = context.getBeanFactory(); // bean工厂
        Map<String, Object> beansMap = beanFactory.getBeansMap(); // beans map 集合
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


}

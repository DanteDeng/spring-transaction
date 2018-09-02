package com.nature.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 上下文组件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContextComponent {

    /**
     * 类型
     * @return 类型
     */
    int type() default 1;

    /**
     * id
     * @return id
     */
    String id() default "";
}

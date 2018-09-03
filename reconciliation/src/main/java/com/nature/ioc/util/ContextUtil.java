package com.nature.ioc.util;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.ioc.annotations.InjectBean;
import com.nature.ioc.definitions.Context;

@ContextComponent
public class ContextUtil {

    @InjectBean
    private static Context context;

    public static Object getBean(String id) {
        return context.getBean(id);
    }

    public static <T> T getBean(Class<T> aClass) {
        return context.getBean(aClass);
    }

    public static <T> T getBean(String id, Class<T> aClass) {
        return context.getBean(id, aClass);
    }
}

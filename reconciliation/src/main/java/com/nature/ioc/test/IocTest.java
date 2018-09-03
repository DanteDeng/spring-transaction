package com.nature.ioc.test;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.ioc.annotations.InjectBean;
import com.nature.ioc.appstarter.NatureAppStarter;
import com.nature.ioc.definitions.AppStarter;

/**
 * ioc测试
 */
@ContextComponent
public class IocTest {

    /**
     * 静态注入testa
     */
    @InjectBean
    private static Testa testa;

    public static void main(String[] args) {
        // 启动容器
        AppStarter starter = new NatureAppStarter();
        starter.start();
        // 接口调用
        testa.aSay();
    }
}

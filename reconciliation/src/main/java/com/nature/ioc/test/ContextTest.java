package com.nature.ioc.test;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.ioc.annotations.InjectBean;
import com.nature.ioc.appstarter.NatureAppStarter;
import com.nature.ioc.definitions.AppStarter;

@ContextComponent
public class ContextTest {

    @InjectBean
    private static Testa testa;

    public static void main(String[] args) {
        AppStarter starter = new NatureAppStarter();
        starter.start();
        testa.aSay();
    }
}

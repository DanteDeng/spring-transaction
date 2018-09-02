package com.nature.ioc.test.impl;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.ioc.annotations.InjectBean;
import com.nature.ioc.test.Testa;
import com.nature.ioc.test.Testb;

@ContextComponent
public class TestaImpl implements Testa {

    @InjectBean
    private Testb testb;

    @Override
    public void aSay() {
        System.out.println("b.....................................................");
        testb.bSay();
    }
}

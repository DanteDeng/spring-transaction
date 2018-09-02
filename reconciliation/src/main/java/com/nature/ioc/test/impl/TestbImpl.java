package com.nature.ioc.test.impl;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.ioc.test.Testb;

@ContextComponent
public class TestbImpl implements Testb {
    @Override
    public void bSay() {
        System.out.println("b.....................................................");
    }
}

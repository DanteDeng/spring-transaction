package com.nature.reconciliation.param;

import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.resource.DemoReconciliationResource;

/**
 * 对账资源
 */
public class CommonReconciliationParam implements ReconciliationParam<String, DemoReconciliationResource> {
    /**
     * 比对数据的key
     */
    private String key;
    /**
     * 比对方
     */
    private DemoReconciliationResource aSide;
    /**
     * 被比对方
     */
    private DemoReconciliationResource bSide;
    /**
     * 比对结果
     */
    private boolean checkResult;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public DemoReconciliationResource getaSide() {
        return aSide;
    }

    @Override
    public void setaSide(DemoReconciliationResource aSide) {
        this.aSide = aSide;
    }

    @Override
    public DemoReconciliationResource getbSide() {
        return bSide;
    }

    @Override
    public void setbSide(DemoReconciliationResource bSide) {
        this.bSide = bSide;
    }

    @Override
    public boolean getCheckResult() {
        return checkResult;
    }

    @Override
    public void setCheckResult(boolean checkResult) {
        this.checkResult = checkResult;
    }

    @Override
    public String toString() {
        return "CommonReconciliationParam{" +
                "key=" + key +
                ", aSide=" + aSide +
                ", bSide=" + bSide +
                ", checkResult=" + checkResult +
                '}';
    }
}

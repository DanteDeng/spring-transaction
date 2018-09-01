package com.nature.reconciliation.preprocessor;

import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.definitions.ReconciliationPreProcessor;
import com.nature.reconciliation.resource.DemoReconciliationResource;
import com.nature.reconciliation.utils.MemoryCacheUtil;

public class DemoReconciliationPreProcessor implements ReconciliationPreProcessor<String, DemoReconciliationResource> {

    @Override
    public void preHandle(ReconciliationParam<String, DemoReconciliationResource> param) {
        String key = param.getKey();
        DemoReconciliationResource aSide = (DemoReconciliationResource) MemoryCacheUtil.getHash(CacheKey.ASIDE_KEY, key);
        DemoReconciliationResource bSide = (DemoReconciliationResource) MemoryCacheUtil.getHash(CacheKey.BSIDE_KEY, key);
        //System.out.println(String.format("pre handle key = %s aSide = %s bSide = %s", key, aSide, bSide));
        if (aSide == null || bSide == null) {
            throw new RuntimeException(String.format("pre handle error key = %s", key));
        }
        param.setaSide(aSide);
        param.setbSide(bSide);
    }
}

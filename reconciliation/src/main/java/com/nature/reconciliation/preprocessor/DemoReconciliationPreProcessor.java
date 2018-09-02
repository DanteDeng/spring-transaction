package com.nature.reconciliation.preprocessor;

import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.definitions.ReconciliationPreProcessor;
import com.nature.reconciliation.resource.DemoReconciliationResource;
import com.nature.reconciliation.utils.MemoryCacheUtil;

/**
 * 示例前置处理器
 */
public class DemoReconciliationPreProcessor implements ReconciliationPreProcessor<String, DemoReconciliationResource> {

    /**
     * 前置处理
     * @param param 对账参数
     */
    @Override
    public void preHandle(ReconciliationParam<String, DemoReconciliationResource> param) {
        String key = param.getKey(); // 对账数据唯一标识
        DemoReconciliationResource aSide = (DemoReconciliationResource) MemoryCacheUtil.getHash(CacheKey.ASIDE_KEY, key); //获取比对方
        DemoReconciliationResource bSide = (DemoReconciliationResource) MemoryCacheUtil.getHash(CacheKey.BSIDE_KEY, key); //获取被比对方
        //System.out.println(String.format("pre handle key = %s aSide = %s bSide = %s", key, aSide, bSide));
        if (aSide == null || bSide == null) {
            throw new RuntimeException(String.format("pre handle error key = %s", key));
        }
        param.setaSide(aSide);
        param.setbSide(bSide);
    }
}

package com.nature.reconciliation.postprocessor;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.definitions.ReconciliationPostProcessor;
import com.nature.reconciliation.resource.DemoReconciliationResource;
import com.nature.reconciliation.utils.MemoryCacheUtil;

/**
 * 示例对账后置处理器
 */
@ContextComponent
public class DemoReconciliationPostProcessor implements ReconciliationPostProcessor<String, DemoReconciliationResource> {

    /**
     * 后置处理
     * @param param 对账参数
     */
    @Override
    public void postHandle(ReconciliationParam<String, DemoReconciliationResource> param) {
        boolean checkResult = param.getCheckResult();
        DemoReconciliationResource aSide = param.getaSide();
        DemoReconciliationResource bSide = param.getbSide();
        if (checkResult) {  // 回写对账结果
            aSide.setStatus("Y");
            bSide.setStatus("Y");
            MemoryCacheUtil.incrementAndGet(CacheKey.DATA_COUNT_SUCCESS); //成功量统计
        } else {
            aSide.setStatus("F");
            bSide.setStatus("F");
            MemoryCacheUtil.incrementAndGet(CacheKey.DATA_COUNT_FAILURE); //失败量统计
            MemoryCacheUtil.addToSet(CacheKey.KEYS_SET_FAILURE, param.getKey()); //失败数据记录
        }
        //System.out.println(String.format("post handle compare result = %s key = %s ", checkResult, param.getKey()));
    }
}

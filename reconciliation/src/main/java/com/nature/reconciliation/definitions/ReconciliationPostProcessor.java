package com.nature.reconciliation.definitions;

/**
 * 对账后置处理器
 */
public interface ReconciliationPostProcessor<K, T extends ReconciliationResource> {

    /**
     * 后置处理
     * @param param 对账参数
     */
    void postHandle(ReconciliationParam<K, T> param);

}

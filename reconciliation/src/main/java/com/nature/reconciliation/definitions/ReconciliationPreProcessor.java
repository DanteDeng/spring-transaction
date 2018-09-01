package com.nature.reconciliation.definitions;

/**
 * 对账前置处理器
 */
public interface ReconciliationPreProcessor<K, T extends ReconciliationResource> {

    /**
     * 前置处理
     * @param param 对账参数
     */
    void preHandle(ReconciliationParam<K, T> param);
}

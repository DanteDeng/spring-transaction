package com.nature.reconciliation.definitions;

/**
 * 对账后置处理器
 * @param <K> 对账维度唯一标识类型
 * @param <T> 对账数据类型
 */
public interface ReconciliationPostProcessor<K, T extends ReconciliationResource> {

    /**
     * 后置处理
     * @param param 对账参数
     */
    void postHandle(ReconciliationParam<K, T> param);

}

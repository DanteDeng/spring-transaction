package com.nature.reconciliation.definitions;

/**
 * 对账比较器
 * @param <K> 对账维度唯一标识类型
 * @param <T> 对账数据类型
 */
public interface ReconciliationComparator<K, T extends ReconciliationResource> {

    /**
     * 数据比对
     * @param param 对账参数
     */
    void compare(ReconciliationParam<K, T> param);

}

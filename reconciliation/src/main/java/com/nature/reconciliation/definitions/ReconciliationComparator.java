package com.nature.reconciliation.definitions;

/**
 * 对账比较器
 * @param <T>
 */
public interface ReconciliationComparator<K, T extends ReconciliationResource> {

    /**
     * 数据比对
     * @param param 对账参数
     */
    void compare(ReconciliationParam<K, T> param);

}

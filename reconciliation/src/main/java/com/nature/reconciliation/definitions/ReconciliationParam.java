package com.nature.reconciliation.definitions;

/**
 * 对账参数（同一操作单元共享）
 * @param <K> 对账维度唯一标识类型
 * @param <T> 对账数据类型
 */
public interface ReconciliationParam<K, T extends ReconciliationResource> {
    /**
     * 获取对账唯一标识
     * @return 对账唯一标识
     */
    K getKey();

    /**
     * 设置对账唯一标识
     * @param key 对账唯一标识
     */
    void setKey(K key);

    /**
     * 获取比对方数据
     * @return 比对方数据
     */
    T getaSide();

    /**
     * 设置比对方数据
     * @param aSide 比对方数据
     */
    void setaSide(T aSide);

    /**
     * 获取被比对方数据
     * @return 被比对方数据
     */
    T getbSide();

    /**
     * 设置被比对方数据
     * @param bSide 被比对方数据
     */
    void setbSide(T bSide);

    /**
     * 获取比对结果
     * @return 比对结果
     */
    boolean getCheckResult();

    /**
     * 设置比对结果
     * @param checkResult 比对结果
     */
    void setCheckResult(boolean checkResult);
}

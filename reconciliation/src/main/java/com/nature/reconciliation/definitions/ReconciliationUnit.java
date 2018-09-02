package com.nature.reconciliation.definitions;

/**
 * 对账操作单元
 * @param <K> 对账数据唯一标识类型
 * @param <T> 对账数据类型
 */
public interface ReconciliationUnit<K, T extends ReconciliationResource> {

    /**
     * 设置前置处理器
     * @param preProcessor 前置处理器
     */
    void setPreProcessor(ReconciliationPreProcessor<K, T> preProcessor);

    /**
     * 设置数据比对器
     * @param comparator 数据比对器
     */
    void setComparator(ReconciliationComparator<K, T> comparator);

    /**
     * 设置后置处理器
     * @param postProcessor 后置处理器
     */
    void setPostProcessor(ReconciliationPostProcessor<K, T> postProcessor);

    /**
     * 执行对账
     */
    void execute();

    /**
     * 设置对账取数key
     * @param k 对账取数key
     */
    void setKey(K k);

}

package com.nature.reconciliation.definitions;

import java.util.concurrent.Callable;

/**
 * 对账过程
 * @param <K> 对账数据唯一标识类型
 * @param <T> 对账数据类型
 * @param <R> 对账完成返回结果类型
 */
public interface ReconciliationProcess<K, T extends ReconciliationResource, R> extends Callable<R> {

    /**
     * 添加对账操作单元
     * @param unit 对账操作单元
     */
    void addUnit(ReconciliationUnit<K, T> unit);

}

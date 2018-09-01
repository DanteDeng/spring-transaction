package com.nature.reconciliation.definitions;

import java.util.concurrent.Callable;

/**
 * 对账过程
 * @param <T> 对账资源类
 */
public interface ReconciliationProcess<K, T extends ReconciliationResource, R> extends Callable<R> {

    /**
     * 添加对账操作单元
     * @param unit 对账操作单元
     */
    void addUnit(ReconciliationUnit<K, T> unit);

}

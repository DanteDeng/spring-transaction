package com.nature.reconciliation.definitions;

import java.util.Set;

/**
 * 对账任务
 * @param <T> 对账数据唯一标识类型
 */
public interface ReconciliationTask<T> {

    /**
     * 设置任务列表
     * @param tasks 任务列表
     */
    void setTasks(Set<T> tasks);

    /**
     * 执行列表中任务
     */
    void executeTasks();
}

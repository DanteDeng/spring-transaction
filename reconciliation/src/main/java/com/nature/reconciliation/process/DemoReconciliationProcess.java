package com.nature.reconciliation.process;

import com.nature.reconciliation.definitions.ReconciliationProcess;
import com.nature.reconciliation.definitions.ReconciliationUnit;
import com.nature.reconciliation.resource.DemoReconciliationResource;
import com.nature.reconciliation.utils.MemoryCacheUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 示例对账流程(多线程处理时候的一个线程单元)
 */
public class DemoReconciliationProcess implements ReconciliationProcess<String, DemoReconciliationResource, Void> {
    /**
     * 数据key：取数唯一标识
     */
    private String key;

    /**
     * 初始化
     * @param key 数据key：取数唯一标识
     */
    public DemoReconciliationProcess(String key) {
        this.key = key;
    }

    /**
     * 流程具有的全部单元操作
     */
    private List<ReconciliationUnit<String, DemoReconciliationResource>> units = new ArrayList<>();

    /**
     * 添加对账操作单元
     * @param unit 对账操作单元
     */
    @Override
    public void addUnit(ReconciliationUnit<String, DemoReconciliationResource> unit) {
        unit.setKey(key);
        units.add(unit);
    }

    @Override
    public Void call() throws Exception {
        MemoryCacheUtil.lock(key); // 分布式锁模拟，控制数据幂等
        try {
            // 执行全部需要执行的对账单元
            for (ReconciliationUnit<String, DemoReconciliationResource> unit : units) {
                unit.execute();
            }
        } catch (Exception e) {
            System.err.println(String.format("reconciliation error %s", e));
        } finally {
            MemoryCacheUtil.unlock(key);
        }
        return null;
    }
}

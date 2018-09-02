package com.nature.reconciliation.unit;

import com.nature.reconciliation.definitions.*;
import com.nature.reconciliation.param.CommonReconciliationParam;
import com.nature.reconciliation.resource.DemoReconciliationResource;

/**
 * 示例对账流程
 */
public class DemoReconciliationUnit implements ReconciliationUnit<String, DemoReconciliationResource> {
    /**
     * 对账操作单元共享的对账参数，不同操作步骤数据传递
     */
    private ReconciliationParam<String, DemoReconciliationResource> param;
    /**
     * 前置处理器
     */
    private ReconciliationPreProcessor<String, DemoReconciliationResource> preProcessor;
    /**
     * 比较器
     */
    private ReconciliationComparator<String, DemoReconciliationResource> comparator;
    /**
     * 后置处理器
     */
    private ReconciliationPostProcessor<String, DemoReconciliationResource> postProcessor;

    /**
     * 设置前置处理器
     * @param preProcessor 前置处理器
     */
    @Override
    public void setPreProcessor(ReconciliationPreProcessor<String, DemoReconciliationResource> preProcessor) {
        this.preProcessor = preProcessor;
    }

    /**
     * 设置比较器
     * @param comparator 数据比对器
     */
    @Override
    public void setComparator(ReconciliationComparator<String, DemoReconciliationResource> comparator) {
        this.comparator = comparator;
    }

    /**
     * 设置后置处理器
     * @param postProcessor 后置处理器
     */
    @Override
    public void setPostProcessor(ReconciliationPostProcessor<String, DemoReconciliationResource> postProcessor) {
        this.postProcessor = postProcessor;
    }

    /**
     * 执行对账操作单元
     */
    @Override
    public void execute() {
        preProcessor.preHandle(param);
        comparator.compare(param);
        postProcessor.postHandle(param);
    }

    /**
     * 设置对账数据唯一标识
     * @param key 对账数据唯一标识
     */
    @Override
    public void setKey(String key) {
        param = new CommonReconciliationParam();
        param.setKey(key);
    }
}

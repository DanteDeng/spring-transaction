package com.nature.reconciliation.unit;

import com.nature.reconciliation.definitions.*;
import com.nature.reconciliation.param.CommonReconciliationParam;
import com.nature.reconciliation.resource.DemoReconciliationResource;

/**
 * 示例对账流程
 */
public class DemoReconciliationUnit implements ReconciliationUnit<String, DemoReconciliationResource> {

    private ReconciliationParam<String, DemoReconciliationResource> param;

    private ReconciliationPreProcessor<String, DemoReconciliationResource> preProcessor;

    private ReconciliationComparator<String, DemoReconciliationResource> comparator;

    private ReconciliationPostProcessor<String, DemoReconciliationResource> postProcessor;

    @Override
    public void setPreProcessor(ReconciliationPreProcessor<String, DemoReconciliationResource> preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void setComparator(ReconciliationComparator<String, DemoReconciliationResource> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void setPostProcessor(ReconciliationPostProcessor<String, DemoReconciliationResource> postProcessor) {
        this.postProcessor = postProcessor;
    }

    @Override
    public void execute() {
        preProcessor.preHandle(param);
        comparator.compare(param);
        postProcessor.postHandle(param);
    }

    @Override
    public void setKey(String key) {
        param = new CommonReconciliationParam();
        param.setKey(key);
    }
}

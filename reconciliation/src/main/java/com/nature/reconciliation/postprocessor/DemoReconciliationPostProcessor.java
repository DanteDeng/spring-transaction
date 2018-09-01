package com.nature.reconciliation.postprocessor;

import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.definitions.ReconciliationPostProcessor;
import com.nature.reconciliation.resource.DemoReconciliationResource;

public class DemoReconciliationPostProcessor implements ReconciliationPostProcessor<String, DemoReconciliationResource> {

    @Override
    public void postHandle(ReconciliationParam<String, DemoReconciliationResource> param) {
        boolean checkResult = param.getCheckResult();
        DemoReconciliationResource aSide = param.getaSide();
        DemoReconciliationResource bSide = param.getbSide();
        if (checkResult) {
            aSide.setStatus("Y");
            bSide.setStatus("Y");
        } else {
            aSide.setStatus("F");
            bSide.setStatus("F");
        }
        //System.out.println(String.format("post handle compare result = %s key = %s ", checkResult, param.getKey()));
    }
}

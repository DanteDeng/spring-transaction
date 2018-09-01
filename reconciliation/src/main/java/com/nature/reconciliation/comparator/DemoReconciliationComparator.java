package com.nature.reconciliation.comparator;

import com.nature.reconciliation.definitions.ReconciliationComparator;
import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.resource.DemoReconciliationResource;

public class DemoReconciliationComparator implements ReconciliationComparator<String, DemoReconciliationResource> {

    @Override
    public void compare(ReconciliationParam<String, DemoReconciliationResource> param) {
        DemoReconciliationResource aSide = param.getaSide();
        DemoReconciliationResource bSide = param.getbSide();
        if (aSide.getStatus().equals(bSide.getStatus())) {
            if (aSide.getAmount().compareTo(bSide.getAmount()) == 0) {
                param.setCheckResult(true);
            }
        }
    }
}

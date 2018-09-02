package com.nature.reconciliation.comparator;

import com.nature.reconciliation.definitions.ReconciliationComparator;
import com.nature.reconciliation.definitions.ReconciliationParam;
import com.nature.reconciliation.resource.DemoReconciliationResource;

/**
 * 示例数据比对器
 */
public class DemoReconciliationComparator implements ReconciliationComparator<String, DemoReconciliationResource> {

    /**
     * 比对数据
     * @param param 对账参数
     */
    @Override
    public void compare(ReconciliationParam<String, DemoReconciliationResource> param) {
        DemoReconciliationResource aSide = param.getaSide();
        DemoReconciliationResource bSide = param.getbSide();
        if (aSide.getStatus().equals(bSide.getStatus())) {
            if (aSide.getAmount().compareTo(bSide.getAmount()) == 0) {
                param.setCheckResult(true); //数据比对成功设置结果为true
            }
        }
    }
}

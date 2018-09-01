package com.nature.reconciliation;

import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationDataImporter;
import com.nature.reconciliation.definitions.ReconciliationTaskDispatcher;
import com.nature.reconciliation.importer.DemoReconciliationDataImporter;
import com.nature.reconciliation.task.dispatcher.DemoReconciliationTaskDispatcher;
import com.nature.reconciliation.utils.MemoryCacheUtil;

public class ReconciliationTest {

    public static void main(String[] args) {
        ReconciliationDataImporter importer = new DemoReconciliationDataImporter();
        importer.importData();
        System.out.println(String.format("import data end set size is %s",MemoryCacheUtil.getSet(CacheKey.KEYS_SET).size()));
        ReconciliationTaskDispatcher dispatcher = new DemoReconciliationTaskDispatcher();
        dispatcher.dispatch();
        System.out.println(String.format("reconciliation end set size is %s",MemoryCacheUtil.getSet(CacheKey.KEYS_SET).size()));
    }
}

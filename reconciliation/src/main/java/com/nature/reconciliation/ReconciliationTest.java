package com.nature.reconciliation;

import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationDataImporter;
import com.nature.reconciliation.definitions.ReconciliationTaskDispatcher;
import com.nature.reconciliation.importer.DemoReconciliationDataImporter;
import com.nature.reconciliation.task.dispatcher.DemoReconciliationTaskDispatcher;
import com.nature.reconciliation.utils.MemoryCacheUtil;

/**
 * 对账测试
 */
public class ReconciliationTest {

    public static void main(String[] args) {
        // 导入数据到缓存
        ReconciliationDataImporter importer = new DemoReconciliationDataImporter();
        importer.importData();
        System.out.println(String.format("import data end set size is %s", MemoryCacheUtil.getSet(CacheKey.KEYS_SET).size()));
        // 分发任务并启动对账
        ReconciliationTaskDispatcher dispatcher = new DemoReconciliationTaskDispatcher();
        dispatcher.dispatch();
        System.out.println(String.format("reconciliation end success %s failure %s", MemoryCacheUtil.getInt(CacheKey.DATA_COUNT_SUCCESS), MemoryCacheUtil.getInt(CacheKey.DATA_COUNT_FAILURE)));
    }
}

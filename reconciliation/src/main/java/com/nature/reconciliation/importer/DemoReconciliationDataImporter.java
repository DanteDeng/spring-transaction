package com.nature.reconciliation.importer;

import com.nature.ioc.annotations.ContextComponent;
import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationDataImporter;
import com.nature.reconciliation.resource.DemoReconciliationResource;
import com.nature.reconciliation.utils.MemoryCacheUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 示例数据导入（对账操作前置处理，包括：数据分组、聚合、打包、格式化等）
 */
@ContextComponent
public class DemoReconciliationDataImporter implements ReconciliationDataImporter {
    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(100);
    /**
     * 随机器
     */
    private Random random = new Random(1);

    /**
     * 导入逻辑
     */
    @Override
    public void importData() {
        List<Future<Void>> futures = new ArrayList<>(); // 阻塞线程使用
        for (int i = 0; i < 100000; i++) {
            String serialNo = i + "";   // 数据序列号生成
            Future<Void> future = executorService.submit(() -> {
                int randNo = random.nextInt(100); // 制造1%的异常数据使用
                double d = random.nextDouble(); // 生成金额的小数部分
                int in = random.nextInt(10000); // 生成金额的整数部分
                d = d + in; // 金额
                MemoryCacheUtil.addToSet(CacheKey.KEYS_SET, serialNo); // 数据打包，流水号集合
                DemoReconciliationResource aside = new DemoReconciliationResource();
                aside.setSerialNo(serialNo);
                aside.setAmount(new BigDecimal(d));
                aside.setStatus("N");
                MemoryCacheUtil.setHash(CacheKey.ASIDE_KEY, serialNo, aside);   // 比对方数据加入缓存
                if (randNo == 1) { // 1%几率出现不同金额
                    d = d - random.nextDouble();
                }
                DemoReconciliationResource bside = new DemoReconciliationResource();
                bside.setSerialNo(serialNo);
                bside.setAmount(new BigDecimal(d));
                bside.setStatus("N");
                MemoryCacheUtil.setHash(CacheKey.BSIDE_KEY, serialNo, bside); // 被比对方加入缓存
                return null;
            });
            futures.add(future);
        }
        executorService.shutdown(); // 线程池资源释放
        for (Future<Void> future : futures) { // 阻塞线程（这里暂时未使用分布式处理的逻辑）
            try {
                future.get();
            } catch (Exception e) {
                System.err.println("import data get future error happens");
            }
        }

    }
}

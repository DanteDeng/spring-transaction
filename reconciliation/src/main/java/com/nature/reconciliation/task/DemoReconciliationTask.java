package com.nature.reconciliation.task;

import com.nature.reconciliation.comparator.DemoReconciliationComparator;
import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.*;
import com.nature.reconciliation.postprocessor.DemoReconciliationPostProcessor;
import com.nature.reconciliation.preprocessor.DemoReconciliationPreProcessor;
import com.nature.reconciliation.process.DemoReconciliationProcess;
import com.nature.reconciliation.resource.DemoReconciliationResource;
import com.nature.reconciliation.unit.DemoReconciliationUnit;
import com.nature.reconciliation.utils.MemoryCacheUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 示例对账任务
 */
public class DemoReconciliationTask implements ReconciliationTask<String> {

    private Set<String> tasks;

    private ReconciliationComparator<String, DemoReconciliationResource> comparator = new DemoReconciliationComparator();

    private ReconciliationPreProcessor<String, DemoReconciliationResource> preProcessor = new DemoReconciliationPreProcessor();

    private ReconciliationPostProcessor<String, DemoReconciliationResource> postProcessor = new DemoReconciliationPostProcessor();

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10,
            20,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    /**
     * 设置任务列表
     * @param tasks 任务列表
     */
    @Override
    public void setTasks(Set<String> tasks) {
        this.tasks = tasks;
    }

    /**
     * 执行任务列表
     */
    @Override
    public void executeTasks() {
        new Thread(() -> {
            List<Future<Void>> futures = new ArrayList<>(); // 阻塞线程获取结果使用
            for (String taskId : tasks) { // 多线程处理分配到的任务
                DemoReconciliationProcess process = new DemoReconciliationProcess(taskId);
                ReconciliationUnit<String, DemoReconciliationResource> unit = new DemoReconciliationUnit();
                unit.setComparator(comparator);
                unit.setPreProcessor(preProcessor);
                unit.setPostProcessor(postProcessor);
                process.addUnit(unit);
                futures.add(executor.submit(process));
            }
            executor.shutdown(); // 任务执行完释放线程池资源
            for (Future<Void> future : futures) { // 阻塞线程（整的一个列表的任务执行完才可以去更新任务次数）
                try {
                    future.get();
                } catch (Exception e) {
                    System.err.println("execute task get future error");
                }
            }
            System.out.println(String.format("execute tasks end executor is %s", executor));

            MemoryCacheUtil.decrementAndGet(CacheKey.TASK_COUNT);// 正执行线程数-1
            MemoryCacheUtil.incrementAndGet(CacheKey.TASK_HANDLED); // 已处理任务总数+1
        }).start();
    }
}

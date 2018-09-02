package com.nature.reconciliation.task.dispatcher;

import com.nature.reconciliation.constant.CacheKey;
import com.nature.reconciliation.definitions.ReconciliationTask;
import com.nature.reconciliation.definitions.ReconciliationTaskDispatcher;
import com.nature.reconciliation.task.DemoReconciliationTask;
import com.nature.reconciliation.utils.MemoryCacheUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 示例任务分发（这里虽然使用的是单机多线程，但实际应用的是分布式多机处理方案）
 */
public class DemoReconciliationTaskDispatcher implements ReconciliationTaskDispatcher {
    /**
     * 定时任务线程池
     */
    private ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);

    /**
     * 分发处理逻辑
     */
    @Override
    public void dispatch() {
        // 获取全部数据
        Set<Object> keys = MemoryCacheUtil.getSet(CacheKey.KEYS_SET);
        List<Object> list = new ArrayList<>();
        list.addAll(keys); // 使用list是为了使用sublist功能
        final int taskSize = 10000; // 单个任务的处理数据数量
        final int taskMax = 3;  // 最大同时处理任务数量
        final AtomicInteger handledCount = new AtomicInteger(0);    // 已处理的数据计数器
        final int listSize = list.size();   // 总的处理数量
        int taskTotal = listSize / taskSize + (listSize % taskSize == 0 ? 0 : 1); // 总任务数量计算
        MemoryCacheUtil.setInt(CacheKey.TASK_TOTAL, taskTotal); // 总任务数量加入缓存，后续控制任务结束使用
        final AtomicInteger currCount = new AtomicInteger(0);   // 当前第几个任务
        MemoryCacheUtil.setInt(CacheKey.TASK_COUNT, 0); // 正在同时进行的任务数量
        MemoryCacheUtil.setInt(CacheKey.TASK_HANDLED, 0); // 已处理的任务数量
        MemoryCacheUtil.setInt(CacheKey.DATA_COUNT_SUCCESS, 0); // 成功数据统计
        MemoryCacheUtil.setInt(CacheKey.DATA_COUNT_FAILURE, 0); // 失败数据统计

        ScheduledFuture<?> future = schedule.scheduleAtFixedRate(() -> {
            if (handledCount.get() >= listSize) { // 总处理数据数量达到需要处理的总数据数量则关闭分发任务
                schedule.shutdown();
                System.out.println(String.format("dispatch end schedule is %s", schedule));
                return;
            }

            // 当前任务数量未满并且数据没有全部处理完可以继续开启任务(这个处理可以理解为实际环境中机器数量，这里就像是只有3台机器)
            while (MemoryCacheUtil.getInt(CacheKey.TASK_COUNT) < taskMax && handledCount.get() < listSize) {
                Integer anInt = MemoryCacheUtil.getInt(CacheKey.TASK_COUNT);
                System.out.println(String.format("this time there is %s tasks is executing", anInt));
                int index = currCount.getAndIncrement(); // 理解为分页下表即可
                int fromIndex = index * taskSize; // 分页开始值
                int toIndex = (index + 1) * taskSize; // 分页结束值
                if (toIndex > listSize) {
                    toIndex = listSize;
                }
                List<Object> objects = list.subList(fromIndex, toIndex); // 需要处理的数据拆分（实际分布式环境使用redis拆分）
                Set<String> set = new HashSet<>();
                for (Object obj : objects) {
                    set.add((String) obj);
                }
                // 开启一个任务处理当前分配的数据
                ReconciliationTask<String> task = new DemoReconciliationTask();
                task.setTasks(set);
                task.executeTasks();
                MemoryCacheUtil.incrementAndGet(CacheKey.TASK_COUNT); // 当前正在执行的任务数量+1
                handledCount.addAndGet(toIndex - fromIndex); // 已处理数据量更新
            }

        }, 0, 1, TimeUnit.MILLISECONDS);

        while (true) { // 阻塞线程
            // 任务全部处理完则停止任务，（发送消息通知对账任务全部处理完成，暂时省略此逻辑）
            if (MemoryCacheUtil.getInt(CacheKey.TASK_HANDLED) >= taskTotal) {

                System.out.println(String.format("dispatch end schedule is %s", schedule));

                break;
            }
            try { // 避免结果查询过度频繁
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("dispatch end executor = %s task handled %s", schedule, MemoryCacheUtil.getInt(CacheKey.TASK_HANDLED)));
    }
}

package com.nature.reconciliation;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleTest {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        AtomicInteger num = new AtomicInteger(0);
        executorService.scheduleAtFixedRate(() -> {
            if(num.incrementAndGet() == 10){
                executorService.shutdown();
            }
            System.out.println(1);
        },0,1,TimeUnit.SECONDS);


    }
}

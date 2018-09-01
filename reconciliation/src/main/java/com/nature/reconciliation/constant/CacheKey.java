package com.nature.reconciliation.constant;

/**
 * 缓存key
 */
public interface CacheKey {

    /**
     * 要处理的keys集合
     */
    String KEYS_SET = "keys";
    /**
     * 对比方缓存数据key
     */
    String ASIDE_KEY = "aside";

    /**
     * 被对比方缓存数据key
     */
    String BSIDE_KEY = "bside";
    /**
     * task计数器
     */
    String TASK_COUNT = "task:count";
    /**
     * task总数
     */
    String TASK_TOTAL = "task:total";
    /**
     * task已处理数量
     */
    String TASK_HANDLED = "task:handled";
}

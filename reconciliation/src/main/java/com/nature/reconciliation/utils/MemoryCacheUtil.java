package com.nature.reconciliation.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 内存缓存工具类（模拟redis）
 */
public class MemoryCacheUtil {

    /**
     * 共享资源（缓存区）
     */
    private static Map<String, Object> values = new HashMap<>();
    /**
     * 共享资源（缓存区）
     */
    private static Map<String, Map<String, Object>> hashs = new HashMap<>();
    /**
     * 共享资源（缓存区）
     */
    private static Map<String, Integer> counters = new HashMap<>();
    /**
     * 共享资源（缓存区）
     */
    private static Map<String, Set<Object>> sets = new HashMap<>();
    /**
     * 读写锁
     */
    private static ReadWriteLock valuesReadWriteLock = new ReentrantReadWriteLock();
    /**
     * 读写锁
     */
    private static ReadWriteLock hashsReadWriteLock = new ReentrantReadWriteLock();
    /**
     * 读写锁
     */
    private static ReadWriteLock countersReadWriteLock = new ReentrantReadWriteLock();
    /**
     * 读写锁
     */
    private static ReadWriteLock setsReadWriteLock = new ReentrantReadWriteLock();

    /**
     * 存放数据到缓存
     * @param key   键
     * @param value 值
     * @return 存入的数据
     */
    public static Object set(String key, Object value) {
        Lock writeLock = valuesReadWriteLock.writeLock();
        writeLock.lock();
        try {
            return values.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 从缓存取出数据
     * @param key 键
     * @return 取出的数据
     */
    public static Object get(String key) {
        Lock readLock = valuesReadWriteLock.readLock();
        readLock.lock();
        try {
            return values.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 按key锁定
     * @param key 锁定的key
     * @return 锁定结果
     */
    public static boolean lock(String key) {
        Lock writeLock = valuesReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (values.containsKey(key)) {
                return false;
            }
            values.put(key, null);
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 解锁key
     * @param key 锁定的key
     * @return 结果结果
     */
    public static boolean unlock(String key) {
        Lock readLock = valuesReadWriteLock.readLock();
        readLock.lock();
        try {
            if (values.containsKey(key)) {
                values.remove(key);
                return true;
            }

            return false;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 设置int值
     * @param key   键
     * @param value 值
     * @return 值
     */
    public static Integer setInt(String key, int value) {
        Lock writeLock = countersReadWriteLock.writeLock();
        writeLock.lock();
        try {
            return counters.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取int值
     * @param key 键
     * @return 值
     */
    public static Integer getInt(String key) {
        Lock readLock = countersReadWriteLock.readLock();
        readLock.lock();
        try {
            return counters.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 递增并获取int
     * @param key 键
     * @return 值
     */
    public static Integer incrementAndGet(String key) {
        Lock writeLock = countersReadWriteLock.writeLock();
        writeLock.lock();
        try {
            Integer integer = counters.get(key);
            if (integer != null) {
                integer++;
                counters.put(key, integer);
            }
            return integer;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 递增并获取int
     * @param key 键
     * @return 值
     */
    public static Integer decrementAndGet(String key) {
        Lock writeLock = countersReadWriteLock.writeLock();
        writeLock.lock();
        try {
            Integer integer = counters.get(key);
            if (integer != null) {
                integer--;
                counters.put(key, integer);
            }
            return integer;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 设置hash值
     * @param key     键
     * @param hashKey map中的键
     * @param value   map中的值
     * @return map中的值
     */
    public static Object setHash(String key, String hashKey, Object value) {
        Lock writeLock = hashsReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (hashs.containsKey(key)) {
                return hashs.get(key).put(hashKey, value);
            } else {
                HashMap<String, Object> map = new HashMap<>();
                hashs.put(key, map);
                return map.put(hashKey, value);
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取hash值
     * @param key     键
     * @param hashKey map中的键
     * @return map中的值
     */
    public static Object getHash(String key, String hashKey) {
        Lock readLock = hashsReadWriteLock.readLock();
        readLock.lock();
        try {
            if (hashs.containsKey(key)) {
                return hashs.get(key).get(hashKey);
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 往set中添加元素
     * @param key   键
     * @param value 值
     * @return 添加是否成功
     */
    public static boolean addToSet(String key, Object value) {
        Lock writeLock = setsReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (sets.containsKey(key)) {
                return sets.get(key).add(value);
            } else {
                Set<Object> set = new HashSet<>();
                sets.put(key, set);
                return set.add(value);
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取set
     * @param key 键
     * @return set
     */
    public static Set<Object> getSet(String key) {
        Lock readLock = setsReadWriteLock.readLock();
        readLock.lock();
        try {
            return sets.get(key);
        } finally {
            readLock.unlock();
        }
    }
}

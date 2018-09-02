package com.nature.ioc.definitions;

/**
 * 应用启动器
 */
public interface AppStarter {

    /**
     * 启动
     */
    void start();

    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();

}

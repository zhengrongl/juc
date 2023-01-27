package com.juc.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: admin
 * @date: 2023/1/27
 * @time: 18:56
 * @description:
 *    Runnable 方法创建线程
 */
@Slf4j
public class Test2 {

    public static void main(String[] args) {
        // 创建线程
        Thread thread = new Thread(() -> {
            log.debug("running");
        });

        // 启动线程
        thread.start();

        // 主线程
        log.info("running");
    }


}

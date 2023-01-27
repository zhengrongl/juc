package com.juc.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: admin
 * @date: 2023/1/27
 * @time: 17:09
 * @description:
 *     线程demo01
 */
@Slf4j(topic = "thread")
public class Test1 {

    public static void main(String[] args) {
        // 创建一个线程
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                // 线程内要执行的方法
                log.debug("running...");
            }
        };
        // 给线程起个名字
        thread1.setName("t1");
        // 启动创建的线程
        thread1.start();

        // 执行主线程
        log.info("running...");
    }

}

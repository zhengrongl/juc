package com.juc.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: admin
 * @date: 2023/1/27
 * @time: 19:13
 * @description:
 *    FutureTask 创建线程
 */
@Slf4j
public class Test3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            // 实现多线程的第三种方法可以返回数据
            @Override
            public Integer call() throws Exception {
                // 要执行的任务代码
                log.debug("running...");
                Thread.sleep(1000);
                return 100;
            }
        });

        Thread thread = new Thread(task,"t1");
        thread.start();

        // 主线程阻塞，同步等待 task 执行完毕的结果
        Integer integer = task.get();
        log.info(String.valueOf(integer));
    }


}

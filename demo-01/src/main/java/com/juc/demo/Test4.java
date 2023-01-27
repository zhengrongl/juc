package com.juc.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: admin
 * @date: 2023/1/27
 * @time: 19:18
 * @description:
 *    演示多个线程并发交替执行
 */
@Slf4j
public class Test4 {

    public static void main(String[] args) {
        new Thread(()-> {
            while (true) {
                log.debug("running1...");
            }
        },"t1").start();

        new Thread(()-> {
           while(true) {
               log.debug("running2...");
           }
        },"t2").start();
    }


}

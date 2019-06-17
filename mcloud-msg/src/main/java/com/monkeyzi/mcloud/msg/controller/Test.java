package com.monkeyzi.mcloud.msg.controller;

import cn.hutool.http.HttpRequest;

import java.util.concurrent.CountDownLatch;

/**
 * @author: 高yg
 * @date: 2019/6/16 17:18
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class Test {

    public static void main(String[] args) {
        testCountDownLatch();
    }

    public static void testCountDownLatch(){

        int threadCount = 2000;

        final CountDownLatch latch = new CountDownLatch(threadCount);
        for(int i=0; i< threadCount; i++){

            new Thread(new Runnable() {

                @Override
                public void run() {

                    System.out.println("线程" + Thread.currentThread().getName() + "开始出发");

                    try {
                        HttpRequest.get("http://localhost:8084/redisson").execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程" + Thread.currentThread().getName() + "已到达终点");

                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadCount+"个线程已经执行完毕");
    }
}

package com.monkeyzi.mcloud.msg.controller;

import cn.hutool.http.HttpUtil;
import com.monkeyzi.mcloud.common.core.lock.DistributedLock;
import com.monkeyzi.mcloud.common.redis.lock.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: 高yg
 * @date: 2019/6/13 21:50
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class RedisLockTest {

    private static  final ExecutorService  executorService=Executors.newFixedThreadPool(100);

    private static final CyclicBarrier cyclicBarrier=new CyclicBarrier(100);

    @Resource
    private RedisDistributedLock redisDistributedLock;



    @GetMapping(value = "/lock")
    public void  lock(){
             String key="redisLock";
             try {
                 boolean f=redisDistributedLock.lock(key,100,20,100);
                 if (f){
                     System.out.println(Thread.currentThread().getName()+"caozuo------------");
                     Thread.sleep(3000);

                 }else {
                     throw new IllegalArgumentException("请稍后重试");
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }finally {
                 System.out.println(Thread.currentThread().getName()+"释放锁");
                 redisDistributedLock.releaseLock(key);
             }


    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+"竹北");
                try {
                    cyclicBarrier.await();
                   HttpUtil.get("http://127.0.0.1:8084/lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();

    }

}

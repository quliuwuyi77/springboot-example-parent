package com.hc.service;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockDemo {

    @Autowired
    private RedissonClient singleRedissonClient;

    /**
     * redisson 获取分布式锁，通过看门狗自动续期
     * @param key
     * @return
     */
    public String redissonLockWatchDog(String key){
        //获取一把锁，只要锁的名字一样就是同一把锁
        RLock lock = singleRedissonClient.getLock(key);
        //1）.redisson的自动续期，如果业务超长，运行期间自动续上30s,不用担心业务时间长，锁自动过期被删掉
        //2).加锁得业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认在30s后自动删除
       lock.lock();
        try {
            System.out.println("加锁成功，开始执行业务: " + lock.getName() + " 时间: " + getDateStr(new Date()));
            Thread.sleep(65000);
            System.out.println("加锁成功，结束执行业务: " + lock.getName() + " 时间: " + getDateStr(new Date()));
        } catch (Exception e){
            System.out.println("报错了： " + e);
        } finally{
            //解锁
            System.out.println("释放锁"+lock.getName());
            lock.unlock();
        }
        return "hello";
    }

    /**
     * 如果手动设置时间,到期自动删除，不会进行续期
     * @param key
     * @return
     */
    public String redissonLock(String key,Integer sleepTime){
        //获取一把锁，只要锁的名字一样就是同一把锁
        RLock lock = singleRedissonClient.getLock(key);
        // 10秒以后自动解锁，自动解锁时间一定要大于业务时间
        //在锁时间到了以后，不会自动续期
        lock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，开始执行业务: " + lock.getName() + " 时间: " + getDateStr(new Date()) + "  锁的状态: " + lock.isLocked());
            Thread.sleep(sleepTime*1000L);
            System.out.println("加锁成功，结束执行业务: " + lock.getName() + " 时间: " + getDateStr(new Date()) + "  锁的状态: " + lock.isLocked());
        } catch (Exception e){
            System.out.println("报错了： " + e);
        } finally{
            //解锁
            System.out.println("释放锁: " + lock.getName()  + lock.getName() + " 时间: " + getDateStr(new Date()) + "  锁的状态: " + lock.isLocked());
            if(lock.isLocked()) { // 如果锁已失效，在进行解锁会报错，所以加一个判断
                lock.unlock();
            }
        }
        return "hello";
    }

    public String getDateStr(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "";
        if(date != null){
            dateStr = df.format(date);
        }
        return dateStr;
    }
}

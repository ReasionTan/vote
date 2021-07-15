package com.vote.config;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisDistributedLocks {

    /**
     * 锁  redis KEY前缀
     *
     * @return
     */
    String lockKey();

    /**
     * 唯一标识 ,或者指定设置入参的参数名
     *
     * @return
     */
    String onlyKey() default "";

    /**
     * NX: 如果key不存在则建立
     * xx: 如果key存在则修改其值
     *
     * @return
     */
    String nxxx() default "NX";

    /**
     * EX 设置键的过期时间为 second 秒
     * PX 设置键的过期时间为 millisecond 毫秒
     *
     * @return
     */
    String expx() default "PX";

    /**
     * 过期时间 默认1分钟
     *
     * @return
     */
    long leaseTime() default 60 * 1000;

    /**
     * 500ms拿不到锁, 就认为获取锁失败
     *
     * @return
     */
    long waitTime() default 500;

    /**
     * 是否锁平台 true 加锁的时候会带 平台
     *
     * @return
     */
    boolean isLockPlatform();
}

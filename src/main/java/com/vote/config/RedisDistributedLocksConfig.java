package com.vote.config;

import com.vote.basic.response.ResponseCode;
import com.vote.basic.response.ResponseWrap;
import com.vote.logging.HttpServletRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Order(1)
public class RedisDistributedLocksConfig implements HandlerInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.vote.config.RedisDistributedLocks)")
    public void RedisDistributedLocksConfig() {
    }

    @Around("RedisDistributedLocksConfig()")
    public Object lockAround(ProceedingJoinPoint joinPoint) {
        RedisDistributedLocks redisConfig = getRedisConfig(joinPoint);
        if (redisConfig == null) {
            return ResponseWrap.failure(ResponseCode.CODE_1005);
        }
        String onlyKey = this.getOnlyKey(joinPoint, redisConfig.onlyKey(), redisConfig.isLockPlatform());
        RLock lock = redissonClient.getLock(redisConfig.lockKey() + onlyKey);
        RedissonRedLock redissonRedLock = new RedissonRedLock(lock);
        String str = redisConfig.onlyKey();
        LOGGER.info("aop ???????????? : [?????????]={}  [????????????] ={}", StringUtils.isBlank(str) ? "id_card" : str, joinPoint.getSignature().getName());
        try {
            boolean isLock = redissonRedLock.tryLock(redisConfig.waitTime(), redisConfig.leaseTime(), TimeUnit.MILLISECONDS);
            if (isLock) {
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            LOGGER.error("", throwable);
            return ResponseWrap.failure(ResponseCode.CODE_1006);
        } finally {
            redissonRedLock.unlock();
            LOGGER.info("aop ???????????? : [?????????]={}  [????????????] ={}", StringUtils.isBlank(str) ? "id_card" : str, joinPoint.getSignature().getName());
        }
        return ResponseWrap.failure(ResponseCode.CODE_900);
    }


    private RedisDistributedLocks getRedisConfig(JoinPoint joinPoint) {
        Class<?> clz = joinPoint.getTarget().getClass();
        try {
            Signature sig = joinPoint.getSignature();
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("???????????????????????????");
            }
            MethodSignature methodSignature = (MethodSignature) sig;
            Method currentMethod = clz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            RedisDistributedLocks redisDistributedLocks = currentMethod.getAnnotation(RedisDistributedLocks.class);
            return redisDistributedLocks;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ???????????????
     *
     * @param onlyKeyParameter ??????key?????????
     * @param isLockPlatform   ??????????????????
     * @return
     */
    private String getOnlyKey(ProceedingJoinPoint joinPoint, String onlyKeyParameter, boolean isLockPlatform) {
        String onlyKey = "";
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (NullPointerException e) {
        }
        Map<String, String> queryParam = HttpServletRequestUtil.getParam(request);
        //??????ID
        String platform = queryParam.get(HttpServletRequestUtil.PLATFORM);
        if (StringUtils.isBlank(platform)) {
            //?????? ??????android
            platform = "android";
        }
        if (StringUtils.isBlank(onlyKeyParameter)) {
            //???????????????
            Object o = Arrays.stream(joinPoint.getArgs()).filter(x -> x instanceof Principal).reduce((min, y) -> y).get();
            if (o != null) {
                onlyKey = ((Principal) o).getName();
            }
        } else {
            onlyKey = queryParam.get(onlyKeyParameter);
            if (StringUtils.isBlank(onlyKey)) {
                //query???????????? ???body??????
                Map<String, String> bodyOnlyKey = null;
                try {
                    bodyOnlyKey = HttpServletRequestUtil.getBodyParam(new MyServletRequestWrapper(request));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bodyOnlyKey != null) {
                    onlyKey = bodyOnlyKey.get(onlyKeyParameter);
                }
            }
        }
        //only?????? ?????????
        if (StringUtils.isBlank(onlyKey)) {
            throw new NullPointerException("?????????????????????????????????");
        }
        if (isLockPlatform) {
            return platform + "_" + onlyKey;
        }
        return onlyKey;
    }


}

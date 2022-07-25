package com.neptune.shop.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RedisClientAopAdvice {

    public static final String RedisClientLogExpressions = "execution(* com.neptune.shop.core.cache.redis.RedisClient.*(..))";

    @Pointcut(RedisClientLogExpressions)
    public void log(){

    }


    @Before("log()")
    public void beforeAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("=============================================>>>>");
        for (Object arg : args) {
            log.info("{}: {}", arg.getClass().getSimpleName(), arg);
        }
        log.info("<<<<=============================================");
    }

}

package com.fly.aspect;

import com.fly.annotation.RateLimit;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zchengfeng
 * @Date 2024/1/26 13:26:23
 */
@Component
@Aspect
public class RateLimitAspect {

    @Pointcut("@annotation(rateLimit)")
    public void rateLimitPointcut() {

    }

    public static final double PERMITS_PER_SECOND = 2;

    private final RateLimiter rateLimiter = RateLimiter.create(PERMITS_PER_SECOND);


    @Around("rateLimitPointcut()")
    public Object limitRequest(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        rateLimiter.setRate(rateLimit.value());
        // 尝试获取令牌,如果能获取到则正常放行,否则抛出异常
        if (rateLimiter.tryAcquire(rateLimit.duration(), TimeUnit.SECONDS)) {
            return joinPoint.proceed();
        }
        throw new RuntimeException("Rate limit exceeded");
    }
}

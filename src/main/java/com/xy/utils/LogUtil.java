package com.xy.utils;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author x1yyy
 */
@Component
public class LogUtil {
    Logger logger = Logger.getLogger(LogUtil.class);

    String log = null;

    /**
     * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点前的执行
     * @param joinPoint 连接点：程序执行过程中的某一行为
     */
    public void before(JoinPoint joinPoint) {
        log = "Class: " + joinPoint.getTarget().getClass().getName() + "  --- Method:  " + joinPoint.getSignature().getName() + "  ---- Start ----  ";
        logger.info(log);
    }

    /**
     * 环绕通知：包围一个连接点的通知，可以在方法的调用前后完成自定义的行为，也可以选择不执行。
     * 类似web中Servlet规范中Filter的doFilter方法。
     * @param joinPoint 当前进程中的连接点
     */
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log = "Class: " + joinPoint.getTarget().getClass().getName() + "  --- Method:  " + joinPoint.getSignature().getName();
            log = log + " ---- Error: " + throwable;
            logger.error(log);
        }

        return result;
    }

    /**
     * 后置通知：在某连接点之之后执行的通知
     * @param joinPoint 连接点：程序执行过程中的某一行为
     */
    public void after(JoinPoint joinPoint) {
        log = "Class: " + joinPoint.getTarget().getClass().getName() + "  --- Method:  " + joinPoint.getSignature().getName() + "  ---- End ----  ";
        logger.info(log);
    }
}

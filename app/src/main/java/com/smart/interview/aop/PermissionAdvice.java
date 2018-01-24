package com.smart.interview.aop;

import com.blankj.utilcode.util.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Percy on 1-24 0024.
 */
@Aspect
public class PermissionAdvice {

    @Pointcut("execution(@com.smart.interview.aop.Permission * *(..))")
    public void pointcut() {}

    @After("pointcut()")
    public void checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        ToastUtils.showShort("切入成功");

    }
}

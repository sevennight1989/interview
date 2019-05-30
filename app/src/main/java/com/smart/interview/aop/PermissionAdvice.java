package com.smart.interview.aop;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import static android.content.ContentValues.TAG;

/**
 * Created by Percy on 1-24 0024.
 */
@Aspect
public class PermissionAdvice {

    public static boolean IS_CONNECT = false;

    public static boolean INTERRUPT = false;

    @Pointcut("execution(@com.smart.interview.aop.Permission * *(..))")
    public void pointcut() {}

    @After("pointcut()")
    public void checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        ToastUtils.showShort("切入成功");
    }

//    @Around("execution(* com.aop.test.Net.getNetStatus())")
//    public boolean after(ProceedingJoinPoint point) {
////        ToastUtils.showShort("拦截网络");
//        return IS_CONNECT;
//    }

    @Around("execution(* com.smart.interview.NetStatus.getNetStatus(..))")
    public int around(ProceedingJoinPoint point) {
        Log.d("PengLog","<==>around ");
        if (INTERRUPT) {
            return 0;
        } else {
            try {
                return (int) point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return 0;
    }



//    @Around("execution(* android.util.Log.d(..))")
//    public void around(ProceedingJoinPoint point) {
//        ToastUtils.showShort("拦截日志");
//        try {
//            point.proceed(point.getArgs());
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    @Before("execution(* android.app.Activity.on**(..))")
//    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
//        String key = joinPoint.getSignature().toString();
//        Log.d("PengLog", "onActivityMethodBefore: " + key);
//    }

}

package com.chenqiao.aopdemo;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by chenqiao on 2019/5/21.
 * e-mail : mrjctech@gmail.com
 */

@Aspect
public class CustomEventAspect {

    private static final String TAG= "CustomEventAspect";


    @Around("execution(@com.chenqiao.aopdemo.DebugTool * *(..))")
    public void getCostTime(ProceedingJoinPoint joinPoint){

        if (!BuildConfig.DEBUG){
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else {
            Signature signature = joinPoint.getSignature();
            long l = System.currentTimeMillis();

            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            Log.d(TAG, signature.getDeclaringType() +"."+signature.getName() + " cost : " +(System.currentTimeMillis()-l)+" ms");
        }



    }


}

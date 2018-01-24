package com.a1byone.bloodpressure.application;

import android.util.Log;

/**
 * 全局异常捕获器
 * Created by Administrator on 2018-01-22.
 */

public class CauchExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static CauchExceptionHandler cauchExceptionHandler = null;

    private CauchExceptionHandler() {

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("CauchExceptionHandler", throwable.getMessage()); //异常信息
    }

    public static CauchExceptionHandler getInstance() {
        if (cauchExceptionHandler == null) {
            synchronized (CauchExceptionHandler.class) {
                if (cauchExceptionHandler == null) {
                    cauchExceptionHandler = new CauchExceptionHandler();
                }
            }
        }
        return  cauchExceptionHandler;
    }

    /**
     * 在Application开始时调用
     */
    public void setDefaultUnCachExceptionHandler() {
        //设置应用默认的全局捕获异常器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}

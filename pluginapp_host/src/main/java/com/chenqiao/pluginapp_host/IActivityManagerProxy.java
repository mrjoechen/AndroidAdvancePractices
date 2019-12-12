package com.chenqiao.pluginapp_host;

import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chenqiao on 2019-12-12.
 * e-mail : mrjctech@gmail.com
 */
public class IActivityManagerProxy implements InvocationHandler {//动态代理

    private final Object am;

    public IActivityManagerProxy(Object am) {//传入代理的AMS对象
        this.am = am;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {//startActivity方法
            Intent oldIntent = null;
            int i = 0;
            for (; i < args.length - 1; i++) {//获取startActivity Intent参数
                if (args[i] instanceof Intent) {
                    oldIntent = (Intent) args[i];
                    break;
                }
            }
            Intent newIntent = new Intent();//创建新的Intent
            newIntent.setClassName("com.chenqiao.pluginapp_host", "com.chenqiao.pluginapp_host.SubActivity");//启动目标SubActivity
            newIntent.putExtra(HookHelper.TRANSFER_INTENT, oldIntent);//保留原始intent
            args[i] = newIntent;//把插件Intent替换为占坑Intent
        }
        return method.invoke(am, args);
    }
}

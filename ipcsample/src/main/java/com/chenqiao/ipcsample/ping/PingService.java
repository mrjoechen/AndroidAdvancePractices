package com.chenqiao.ipcsample.ping;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chenqiao.ipcsample.Utils;

/**
 * Created by chenqiao on 2020-02-08.
 * e-mail : mrjctech@gmail.com
 */
public class PingService extends Service {


    public static final String ACTION_START_PINGNET = "com.chenqiao.ipcsample.ping.PINGNET";
    public static final String TAG = "PingService";



    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Utils.logggg(TAG, ""+ intent.getAction());
        if (ACTION_START_PINGNET.equals(intent.getAction())){
            return new PingAidlImpl();
        }
        return null;
    }
}

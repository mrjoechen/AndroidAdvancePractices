package com.chenqiao.ipcsample.ping;

import android.os.Handler;
import android.os.RemoteException;

import com.chenqiao.ipcsample.Utils;

/**
 * Created by chenqiao on 2020-02-09.
 * e-mail : mrjctech@gmail.com
 * Running in main process
 */
public class PingAidlCallbackImpl extends PingAidlCallback.Stub {

    private static final String TAG = "PingAidlCallbackImpl";

    private Handler mHandler = new Handler();
    public PingAidlCallbackImpl(PingRefreshCallback pingRefreshCallback) {
        mPingRefreshCallback = pingRefreshCallback;
    }

    private PingRefreshCallback mPingRefreshCallback;
    @Override
    public void onCallback(final long ping) throws RemoteException {

        //CallBack in Binder Thread of Main Process
        Utils.logggg(TAG, "onCallback ");

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPingRefreshCallback != null){
                    mPingRefreshCallback.onRefresh(ping);
                }
            }
        });
    }

//    @Override
//    public void unbind() throws RemoteException {
//
//        if (mHandler != null){
//            mHandler.removeCallbacksAndMessages(null);
//        }
//
//        mPingRefreshCallback = null;
//    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

}

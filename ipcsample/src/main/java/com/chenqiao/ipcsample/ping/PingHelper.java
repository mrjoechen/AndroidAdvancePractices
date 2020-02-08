package com.chenqiao.ipcsample.ping;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.chenqiao.ipcsample.Utils;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.chenqiao.ipcsample.ping.PingService.ACTION_START_PINGNET;


/**
 * Created by chenqiao on 2020-02-09.
 * e-mail : mrjctech@gmail.com
 */
public class PingHelper {


    private static final String TAG = "PingHelper";
    public static final String PING_URL = "www.baidu.com";

    private static final int TIME_OUT = 3000;
    private static final int PING_INTERVAL = 4000;
    public static final int WARNNING_PING = 300;
    private Context mContext;

    private boolean mBound;
    private PingAidlInterface mPingAidlInterface;

    private PingHelper(){

    }

    static class Holder{
        static PingHelper instance = new PingHelper();
    }

    public static PingHelper getInstance(){
        return Holder.instance;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            Utils.logggg(TAG, "ping connected to target");
            mPingAidlInterface = PingAidlInterface.Stub.asInterface(service);
            try {
                mPingAidlInterface.ping(PING_URL, TIME_OUT, PING_INTERVAL);
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mBound) {
                mBound = false;
                Utils.logggg(TAG, "ping service disconnected!!!");
            }
        }
    };


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            // TODO Auto-generated method stub
            if (mPingAidlInterface == null){
                return;
            }
            mPingAidlInterface.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mPingAidlInterface = null;
            // TODO:重新绑定远程服务
            if (mContext != null){
                startPing(mContext);
            }
        }
    };


    public PingAidlCallbackImpl registerRemoteCallBack(PingRefreshCallback pingRefreshCallback) {
        //todo bug Maybe bind(startPing()) operate have not complete when register operate is executed
        if (mPingAidlInterface != null) {
            try {
                PingAidlCallbackImpl callback = new PingAidlCallbackImpl(pingRefreshCallback);
                mPingAidlInterface.registerCallback(callback);
                return callback;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void unRegisterRemoteCallback(PingAidlCallbackImpl pingAidlCallback) {
        if (mPingAidlInterface != null) {
            try {
                mPingAidlInterface.unRegisterCallback(pingAidlCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void startPing(Context context) {
        if (!mBound) {
            mContext = context;
            Intent bindIntent = new Intent();
            bindIntent.setClassName(context, PingService.class.getName());
            bindIntent.setAction(ACTION_START_PINGNET);
            context.bindService(bindIntent, mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    public void stopPing(Context context) {
        if (mBound) {
            if (mPingAidlInterface != null) {
                try {
                    mPingAidlInterface.stopPing();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            context.unbindService(mServiceConnection);
            mBound = false;
        }
    }


}

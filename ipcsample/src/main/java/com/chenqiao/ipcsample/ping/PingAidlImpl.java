package com.chenqiao.ipcsample.ping;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.chenqiao.ipcsample.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqiao on 2020-02-08.
 * e-mail : mrjctech@gmail.com
 * Running in remote process
 */
public class PingAidlImpl extends PingAidlInterface.Stub {

    private static final String TAG = "PingAidlImpl";
    private RemoteCallbackList<PingAidlCallback> mPingAidlCallbacks = new RemoteCallbackList<>();
    private HandlerThread handlerThread;
    private Handler mHandler;


    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void ping(String host, int timeout, int interval) throws RemoteException {
        Utils.logggg(TAG, "ping() ");
        startPing(host, timeout, interval);
    }

    @Override
    public void registerCallback(PingAidlCallback callback) throws RemoteException {
        Utils.logggg(TAG, "registerCallback() ");

        if (callback != null){
            mPingAidlCallbacks.register(callback);
        }
    }

    @Override
    public void unRegisterCallback(PingAidlCallback callback) throws RemoteException {
        if (callback != null){
            mPingAidlCallbacks.unregister(callback);
        }
    }

    @Override
    public void stopPing() throws RemoteException {
        if (mPingAidlCallbacks != null){
            mPingAidlCallbacks.kill();
        }

        mHandler.removeCallbacksAndMessages(null);
        if (handlerThread.isAlive()){
            handlerThread.quitSafely();
            handlerThread = null;
        }
    }


    private void startPing(final String host, final int timeout, final int interval){

        Utils.logggg(TAG, "startPing() ");

        if (handlerThread == null){
            handlerThread = new HandlerThread("ping net handlerThread");
        }

        if (!handlerThread.isAlive()){
            handlerThread.start();

            if (mHandler == null){
                mHandler = new Handler(handlerThread.getLooper());
            }
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long l = 0;

                try {
                    //Running in HandlerThread of remote process
                    l = checkServiceValidWithException(host, timeout);
                    Utils.logggg(TAG, "pingBinder " + l+"ms");
                    broadcastPing(l);

                } catch (IOException e) {
                    e.printStackTrace();
                    broadcastPing(0);
                }
                mHandler.postDelayed(this, interval);

            }
        }, interval);
    }




    private void broadcastPing(long ping){

        Utils.logggg(TAG, "broadcastPing " + ping+"ms");

        int callBackSize = mPingAidlCallbacks.beginBroadcast();
        if (callBackSize == 0) {
            return;
        }
        for (int i = 0; i < callBackSize; i++) {
            try {
                mPingAidlCallbacks.getBroadcastItem(i).onCallback(ping);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mPingAidlCallbacks.finishBroadcast();
    }

    public static long checkServiceValidWithException(String host, int timeout) throws IOException {

        Utils.logggg(TAG, "checkServiceValidWithException ");

        long startTimeTemp = SystemClock.elapsedRealtime();
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(host, 80);
        socket.connect(socketAddress, timeout);
        socket.close();
        long stopTimeTemp = SystemClock.elapsedRealtime();

        return stopTimeTemp - startTimeTemp;
    }

}

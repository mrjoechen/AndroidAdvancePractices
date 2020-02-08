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
 */
public class PingAidlImpl extends PingAidlInterface.Stub {

    private RemoteCallbackList<PingAidlCallback> mPingAidlCallbacks = new RemoteCallbackList<>();
    private Handler mHandler;
    private HandlerThread handlerThread;

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void ping(String host, int timeout, int interval) throws RemoteException {
        startPing(host, timeout, interval);
    }

    @Override
    public void registerCallback(PingAidlCallback callback) throws RemoteException {
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
    }


    private void startPing(final String host, final int timeout, final int interval){

        handlerThread = new HandlerThread("ping net handlerThread");
        if (!handlerThread.isAlive()){
            handlerThread.start();
        }

        mHandler = new Handler(handlerThread.getLooper());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long l = 0;

                try {
                    l = checkServiceValidWithException(host, timeout);
                    Utils.logggg("pingBinder", l+"ms");
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
        long startTimeTemp = SystemClock.elapsedRealtime();
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(host, 80);
        socket.connect(socketAddress, timeout);
        socket.close();
        long stopTimeTemp = SystemClock.elapsedRealtime();

        return stopTimeTemp - startTimeTemp;
    }

}

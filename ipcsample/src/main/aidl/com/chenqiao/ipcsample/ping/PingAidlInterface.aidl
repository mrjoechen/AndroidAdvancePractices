// PingAidlInterface.aidl
package com.chenqiao.ipcsample.ping;

// Declare any non-default types here with import statements
import com.chenqiao.ipcsample.ping.PingAidlCallback;

interface PingAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void ping(String host, int timeout, int interval);

    void registerCallback(PingAidlCallback callback);

    void unRegisterCallback(PingAidlCallback callback);

    void stopPing();

}

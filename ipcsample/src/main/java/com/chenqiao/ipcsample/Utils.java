package com.chenqiao.ipcsample;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chenqiao on 2020-02-09.
 * e-mail : mrjctech@gmail.com
 */
public class Utils {



    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static void logggg(String tag, String msg){

        Log.d("aaaprocess:["+getProcessName(android.os.Process.myPid()) +"]"+"thread:["+ Thread.currentThread().getName() +"]:"+ tag, msg);
    }
}

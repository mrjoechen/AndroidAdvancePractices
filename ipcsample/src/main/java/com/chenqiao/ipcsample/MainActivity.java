package com.chenqiao.ipcsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chenqiao.ipcsample.ping.PingAidlCallbackImpl;
import com.chenqiao.ipcsample.ping.PingHelper;
import com.chenqiao.ipcsample.ping.PingRefreshCallback;

public class MainActivity extends AppCompatActivity implements PingRefreshCallback {

    private PingAidlCallbackImpl mPingAidlCallback;
    private TextView tvDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDuration = findViewById(R.id.tv_duration);
        PingHelper.getInstance().startPing(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //todo bug Maybe bind(startPing()) operate have not complete when register is executed
        mPingAidlCallback = PingHelper.getInstance().registerRemoteCallBack(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPingAidlCallback != null){
            PingHelper.getInstance().unRegisterRemoteCallback(mPingAidlCallback);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PingHelper.getInstance().stopPing(this);

    }

    @Override
    public void onRefresh(long ping) {

        tvDuration.setText(ping + " ms");
    }
}

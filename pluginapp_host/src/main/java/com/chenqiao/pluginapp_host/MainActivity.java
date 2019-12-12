package com.chenqiao.pluginapp_host;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tv;
    private Button btn_test;

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_test);

        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        Class<?> clazzz = null;
        try {
            clazzz = Class.forName("com.chenqiao.pluginapp_plugin.TestPluginUtil");
            System.out.println("类名：" + clazzz.getName());

            Method method = clazzz.getMethod("printHello");
            method.invoke(clazzz);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test:

                Class<?> clazzz = null;
                try {
                    clazzz = Class.forName("com.chenqiao.pluginapp_plugin.TestPluginUtil");
                    System.out.println("类名：" + clazzz.getName());

                    Object o = clazzz.newInstance();
                    Method add = clazzz.getMethod("add", int.class, int.class);
                    Integer invoke = (Integer) add.invoke(o, 1, 2);

                    tv.setText("" + invoke);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_start:


                Intent intent = null;
                intent = new Intent();
                intent.setClassName("com.chenqiao.pluginapp_plugin", "com.chenqiao.pluginapp_plugin.PluginActivity");
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}

package com.chenqiao.aopdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    @DebugTool
    @Override
    protected void onResume() {
        super.onResume();

    }


    @DebugTool
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn:

                System.out.println("Onclick");

                break;
            default:
                break;
        }

    }
}
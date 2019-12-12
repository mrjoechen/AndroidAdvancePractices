package com.chenqiao.pluginapp_plugin;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class PluginActivity extends Activity {//这里需要注意继承的是Activity不是AppCompatActivity，因为AppCompatActivity做了很多检查用它的话还需要多hook几个类，而我们主要是流程和原理的掌握就没有进行适配了。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
    }

    @Override
    public Resources getResources() {//重写getResources()是因为对于activity中通过id获取资源的Resources都是通过该方法获取
        return getApplication() != null && getApplication().getResources() != null ? getApplication().getResources() : super.getResources();//拿到插件Resources
    }
}

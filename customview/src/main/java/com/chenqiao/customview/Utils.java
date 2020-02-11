package com.chenqiao.customview;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by chenqiao on 2020-02-11.
 * e-mail : mrjctech@gmail.com
 */


public class Utils {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}

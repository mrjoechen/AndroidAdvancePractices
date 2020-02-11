package com.chenqiao.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by chenqiao on 2020-02-11.
 * e-mail : mrjctech@gmail.com
 */

public class PieChart extends View {
//    private static final float RADIUS = Utils.dp2px(150);
    private static final float OFFSET_LENGTH = Utils.dp2px(10);
    private static final int OFFSET_INDEX = 2;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bounds = new RectF();
    int[] ANGLES = {60, 100, 120, 80};
    int[] COLORS = {Color.parseColor("#448AFF"), Color.parseColor("#9575CD"),
            Color.parseColor("#FF8F00"), Color.parseColor("#00C853")};

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float r = getWidth()/2-100;

        bounds.set(getWidth() / 2f - r, getHeight() / 2f - r,
                getWidth() / 2f + r, getHeight() / 2f + r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentAngle = 0;
        for (int i = 0; i < ANGLES.length; i++) {
            if (i == OFFSET_INDEX) {
                canvas.save();
                canvas.translate(OFFSET_LENGTH * (float) Math.cos(Math.toRadians(currentAngle + ANGLES[i] / 2f)),
                        OFFSET_LENGTH * (float) Math.sin(Math.toRadians(currentAngle + ANGLES[i] / 2f)));
            }
            paint.setColor(COLORS[i]);
            canvas.drawArc(bounds, currentAngle, ANGLES[i], true, paint);
            currentAngle += ANGLES[i];
            if (i == OFFSET_INDEX) {
                canvas.restore();
            }
        }
    }
}

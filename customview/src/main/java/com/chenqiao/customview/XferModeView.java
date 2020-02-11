package com.chenqiao.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by chenqiao on 2020-02-12.
 * e-mail : mrjctech@gmail.com
 */
public class XferModeView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap circleBitmap = Bitmap.createBitmap(300,300 , Config.ARGB_8888);

    private Bitmap rectBitmap = Bitmap.createBitmap(300,300 , Config.ARGB_8888);

    private Canvas rectCanvas = new Canvas(rectBitmap);
    private Canvas circleCanvas = new Canvas(circleBitmap);
    private RectF  bounds = new RectF();

    public XferModeView(Context context) {
        super(context);
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bounds.set(0, 0, 300, 300);

        mPaint.setColor(Color.BLUE);
        rectCanvas.drawCircle(150, 50, 50, mPaint);

        mPaint.setColor(Color.RED);
        circleCanvas.drawRect(bounds, mPaint);


        int i = canvas.saveLayer(bounds, mPaint);
        canvas.drawBitmap(circleBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(rectBitmap, 0, 0, mPaint);
        canvas.restoreToCount(i);
        mPaint.setXfermode(null);



    }
}

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

    private Bitmap circleBitmap = Bitmap.createBitmap(500,500 , Config.ARGB_8888);

    private Bitmap rectBitmap = Bitmap.createBitmap(500,500 , Config.ARGB_8888);

    private Canvas rectCanvas = new Canvas(rectBitmap);
    private Canvas circleCanvas = new Canvas(circleBitmap);
    private RectF  bounds = new RectF();

    public XferModeView(Context context) {
        super(context);
        initView();
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        bounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(bounds, mPaint);
//
//////////////////////
//        mPaint.setStyle(Paint.Style.FILL);
//
//        mPaint.setColor(Color.BLUE);
//        rectCanvas.drawRect(0, 0, 200, 300, mPaint);
//
//        mPaint.setColor(Color.RED);
//        circleCanvas.drawCircle(200, 200, 200, mPaint);
//
//
//        int i = canvas.saveLayer(bounds, mPaint);
//        canvas.drawBitmap(circleBitmap, 0, 0, mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(rectBitmap, 0, 0, mPaint);
//        canvas.restoreToCount(i);
//        mPaint.setXfermode(null);



        ////////////////////////
//        mPaint.setStyle(Paint.Style.FILL);
//
//        mPaint.setColor(Color.BLUE);
//
//        int i = canvas.saveLayer(bounds, mPaint);
//
//
//        //画方
//        RectF rectF = new RectF();
//        rectF.set(0, getMeasuredHeight()/2, getMeasuredWidth()/2, getMeasuredHeight());
//        canvas.drawRect(rectF, mPaint);
//
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//
//        //画圆
//        mPaint.setColor(Color.RED);
//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, getMeasuredWidth()/3, mPaint);
//
//        canvas.restoreToCount(i);
//        mPaint.setXfermode(null);


        Bitmap rectBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Canvas canvas1 = new Canvas(rectBitmap);
        paint.setColor(Color.BLUE);
        canvas1.drawRoundRect(0, 0, rectBitmap.getWidth()/2, rectBitmap.getHeight()/2,50, 50, paint);

        out = Bitmap.createBitmap(rectBitmap.getWidth(), rectBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvasout = new Canvas(out);
        //DST
        paint.setColor(Color.RED);
        canvasout.drawCircle(rectBitmap.getWidth()/2, rectBitmap.getHeight()/2, 200, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        //SRC
        canvasout.drawBitmap(rectBitmap, 0, 0, paint);
        paint.setXfermode(null);

        canvas.drawBitmap(out, 0, 0, null);
    }

    private Bitmap out;

    private void initView(){


//        Bitmap rectBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
//
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//        Canvas canvas1 = new Canvas(rectBitmap);
//        paint.setColor(Color.BLUE);
//        canvas1.drawRoundRect(0, 0, rectBitmap.getWidth()/2, rectBitmap.getHeight()/2,50, 50, paint);
//
//        out = Bitmap.createBitmap(rectBitmap.getWidth(), rectBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(out);
//        //DST
//        paint.setColor(Color.RED);
//        canvas.drawCircle(rectBitmap.getWidth()/2, rectBitmap.getHeight()/2, 200, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
////        //SRC
//        canvas.drawBitmap(rectBitmap, 0, 0, paint);
//        paint.setXfermode(null);

    }
}

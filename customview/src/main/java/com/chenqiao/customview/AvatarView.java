package com.chenqiao.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by chenqiao on 2020-02-11.
 * e-mail : mrjctech@gmail.com
 */
public class AvatarView extends View {
    private static float WIDTH = Utils.dp2px(300);
    private static float OFFSET = Utils.dp2px(20);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    RectF bounds = new RectF();

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    {
//        bitmap = getAvatar((int) WIDTH);
//        bounds.set(OFFSET, OFFSET, OFFSET + WIDTH, OFFSET + WIDTH);
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WIDTH = getMeasuredWidth() - OFFSET * 2;
        bitmap = getAvatar((int) WIDTH);
        bounds.set(OFFSET, OFFSET, OFFSET + WIDTH, OFFSET + WIDTH);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float radius = WIDTH / 2f;
        //外框
        canvas.drawCircle(OFFSET + radius, OFFSET + radius, radius + Utils.dp2px(5), paint);
        //缓冲, DST 区域，先保存，否则DST是整个View区域
        int saved = canvas.saveLayer(bounds, paint);
        canvas.drawCircle(OFFSET + radius, OFFSET + radius, radius, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, OFFSET, OFFSET, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.groot, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.groot, options);
    }
}
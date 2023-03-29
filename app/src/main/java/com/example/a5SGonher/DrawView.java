package com.example.a5SGonher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DrawView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private float downX, downY, upX, upY;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                upX = event.getX();
                upY = event.getY();
                canvas.drawLine(downX, downY, upX, upY, paint);
                downX = upX;
                downY = upY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                canvas.drawLine(downX, downY, upX, upY, paint);
                invalidate();
                break;
            default:
                return false;
        }

        return true;
    }

    public void setBitmap(Bitmap bitmap) {

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}

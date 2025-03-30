package com.ahmadabuhasan.canvas;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.Resources;

/**
 * Custom view that follows touch events to draw on a canvas.
 */

public class MyCanvasView extends View {
    private Paint mPaint;
    public static int r = 255;
    public static int g = 255;
    public static int b = 255;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;

    MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context);


        mPaint = new Paint();
        mPaint.setColor(Color.rgb(r, g, b));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL); // default: FILL
        mPaint.setStrokeJoin(Paint.Join.ROUND); // default: MITER
        mPaint.setStrokeCap(Paint.Cap.ROUND); // default: BUTT
        mPaint.setStrokeWidth(12); // default: Hairline-width (really thin)
    }

    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(Color.rgb(255,255,255));
        int inset = 40;

    }
    private static final float TOUCH_TOLERANCE = 4;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mExtraBitmap, 0, 0, null);


    }
    private float mX, mY;
    private void touchStart(float x, float y) {
        mX = x;
        mY = y;
        int left = (int) x / 50 * 50 ;
        int right = ((int) x / 50 * 50) + 50;
        int top = (int) y / 50 * 50 ;
        int bottom = ((int) y / 50 * 50) + 50 ;
        mExtraCanvas.drawRect(left, top, right, bottom, mPaint);


    }
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            int left = (int) x / 50 * 50 ;
            int right = ((int) x / 50 * 50) + 50;
            int top = (int) y / 50 * 50 ;
            int bottom = ((int) y / 50 * 50) + 50 ;
            mExtraCanvas.drawRect(left, top, right, bottom, mPaint);
        }
    }



    private void touchUp() {
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }
        return true;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }}
package com.ahmadabuhasan.canvas;

import androidx.core.content.res.ResourcesCompat;

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

public class MyCanvasView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mDrawColor;
    private int mBackgroundColor;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;

    public int r = 0;
    public int g = 0;
    public int b = 0;

    private Rect mFrame;

    MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context);

        mBackgroundColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_orange, null);
        mDrawColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_yellow, null);

        // Holds the path we are currently drawing.
        mPath = new Path();
        // Set up the paint with which to draw.
        mPaint = new Paint();

        System.out.println(mDrawColor);

        mPaint.setColor(Color.rgb(r, g, b));

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE); // default: FILL
        mPaint.setStrokeJoin(Paint.Join.ROUND); // default: MITER
        mPaint.setStrokeCap(Paint.Cap.ROUND); // default: BUTT
        mPaint.setStrokeWidth(12); // default: Hairline-width (really thin)
    }

    /**
     * Note: Called whenever the view changes size.
     * Since the view starts out with no size, this is also called after
     * the view has been inflated and has a valid size.
     */
    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // Create bitmap, create canvas with bitmap, fill canvas with color.
        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(mBackgroundColor);

        // Calculate the rect a frame around the picture.
        int inset = 40;
        mFrame = new Rect(inset, inset, width - inset, height - inset);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the bitmap that has the saved path.
        canvas.drawBitmap(mExtraBitmap, 0, 0, null);

        // Draw a frame around the picture.
        canvas.drawRect(mFrame, mPaint);
    }

    // Variables for the latest x,y values,
    // which are the starting point for the next path.
    private float mX, mY;
    // Don't draw every single pixel.
    // If the finger has has moved less than this distance, don't draw.
    private static final float TOUCH_TOLERANCE = 4;

    // The following methods factor out what happens for different touch events,
    // as determined by the onTouchEvent() switch statement.
    // This keeps the switch statement
    // concise and and easier to change what happens for each event.

    private void touchStart(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
            // Draw the path in the extra bitmap to save it.
            mExtraCanvas.drawPath(mPath, mPaint);
        }
    }

    private void touchUp() {
        // Reset the path so it doesn't get drawn again.
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Invalidate() is inside the case statements because there are many
        // other types of motion events passed into this listener,
        // and we don't want to invalidate the view for those.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                // No need to invalidate because we are not drawing anything.
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
                // do nothing
        }
        return true;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
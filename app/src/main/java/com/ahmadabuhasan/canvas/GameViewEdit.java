package com.ahmadabuhasan.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ahmad Abu Hasan on 18/10/2020.
 */

public class GameViewEdit extends SurfaceView implements Runnable {

    private boolean mRunning;
    private Thread mGameThread = null;
    private Path mPath;

    private Context mContext;

    private FlashlightConeEdit mFlashlightCone;

    private Paint mPaint;
    private Bitmap mBitmap;
    private RectF mWinnerRect;
    private int mBitmapX;
    private int mBitmapY;
    private int mViewWidth;
    private int mViewHeight;
    private SurfaceHolder mSurfaceHolder;

    public GameViewEdit(Context context) {
        this(context, null);
    }

    public GameViewEdit(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;

        mFlashlightCone = new FlashlightConeEdit(mViewWidth, mViewHeight);

        mPaint.setTextSize(mViewHeight / 5);
        mPaint.getTextAlign();

        mBitmap = BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.mosi_tidak_percaya);
        setUpBitmap();
    }

    public void run() {

        Canvas canvas;

        while (mRunning) {

            if (mSurfaceHolder.getSurface().isValid()) {

                int x = mFlashlightCone.getX();
                int y = mFlashlightCone.getY();
                int radius = mFlashlightCone.getRadius();

                canvas = mSurfaceHolder.lockCanvas();

                canvas.save();
                canvas.drawColor(Color.GREEN);
                canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);

                mPath.addCircle(x, y, radius, Path.Direction.CCW);

                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    canvas.clipPath(mPath, Region.Op.DIFFERENCE);
                } else {
                    canvas.clipOutPath(mPath);
                }

                canvas.drawColor(Color.YELLOW);

                if (x > mWinnerRect.left && x < mWinnerRect.right
                        && y > mWinnerRect.top && y < mWinnerRect.bottom) {
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);
                    canvas.drawText(
                            "COK!", mViewWidth / 3, mViewHeight / 2, mPaint);
                }

                mPath.rewind();

                canvas.restore();

                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void updateFrame(int newX, int newY) {
        mFlashlightCone.update(newX, newY);
    }

    private void setUpBitmap() {
        mBitmapX = (int) Math.floor(
                Math.random() * (mViewWidth - mBitmap.getWidth()));
        mBitmapY = (int) Math.floor(
                Math.random() * (mViewHeight - mBitmap.getHeight()));
        mWinnerRect = new RectF(mBitmapX, mBitmapY,
                mBitmapX + mBitmap.getWidth(),
                mBitmapY + mBitmap.getHeight());
    }

    public void pause() {
        mRunning = false;
        try {

            mGameThread.join();
        } catch (InterruptedException ignored) {
        }
    }

    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setUpBitmap();

                updateFrame((int) x, (int) y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                updateFrame((int) x, (int) y);
                invalidate();
                break;
            default:

        }
        return true;
    }
}
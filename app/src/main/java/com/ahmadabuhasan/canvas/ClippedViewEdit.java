package com.ahmadabuhasan.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ahmad Abu Hasan on 18/10/2020.
 */

public class ClippedViewEdit extends View {

    private Paint mPaint;
    private Path mPath;

    private int mClipRectRight =
            (int) getResources().getDimension(R.dimen.clipRectRight1);
    private int mClipRectBottom =
            (int) getResources().getDimension(R.dimen.clipRectBottom1);
    private int mClipRectTop =
            (int) getResources().getDimension(R.dimen.clipRectTop1);
    private int mClipRectLeft =
            (int) getResources().getDimension(R.dimen.clipRectLeft1);
    private int mRectInset =
            (int) getResources().getDimension(R.dimen.rectInset1);
    private int mSmallRectOffset =
            (int) getResources().getDimension(R.dimen.smallRectOffset1);

    private int mCircleRadius =
            (int) getResources().getDimension(R.dimen.circleRadius1);

    private int mTextOffset =
            (int) getResources().getDimension(R.dimen.textOffset1);
    private int mTextSize =
            (int) getResources().getDimension(R.dimen.textSize1);

    private int mColumnOne = mRectInset;
    private int mColumnnTwo = mColumnOne + mRectInset + mClipRectRight;

    private int mRowOne = mRectInset;
    private int mRowTwo = mRowOne + mRectInset + mClipRectBottom;
    private int mRowThree = mRowTwo + mRectInset + mClipRectBottom;
    private int mRowFour = mRowThree + mRectInset + mClipRectBottom;
    private int mTextRow = mRowFour + (int) (1.5 * mClipRectBottom);

    private final RectF mRectF;

    public ClippedViewEdit(Context context) {
        this(context, null);
    }

    public ClippedViewEdit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(
                (int) getResources().getDimension(R.dimen.strokeWidth1));
        mPaint.setTextSize((int) getResources().getDimension(R.dimen.textSize1));
        mPath = new Path();

        mRectF = new RectF(new Rect(mRectInset, mRectInset,
                mClipRectRight - mRectInset, mClipRectBottom - mRectInset));
    }

    private void drawClippedRectangle(Canvas canvas) {

        canvas.clipRect(mClipRectLeft, mClipRectTop,
                mClipRectRight, mClipRectBottom);

        canvas.drawColor(Color.RED);

        mPaint.setColor(Color.GREEN);
        canvas.drawLine(mClipRectLeft, mClipRectTop,
                mClipRectRight, mClipRectBottom, mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(mCircleRadius, mClipRectBottom - mCircleRadius,
                mCircleRadius, mPaint);

        mPaint.setColor(Color.WHITE);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(getContext().getString(R.string.clipping1),
                mClipRectRight, mTextOffset, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        canvas.save();

        canvas.translate(mColumnOne, mRowOne);
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();

        canvas.translate(mColumnnTwo, mRowOne);

        canvas.clipRect(2 * mRectInset, 2 * mRectInset,
                mClipRectRight - 2 * mRectInset, mClipRectBottom - 2 * mRectInset);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            canvas.clipRect(4 * mRectInset, 4 * mRectInset,
                    mClipRectRight - 4 * mRectInset, mClipRectBottom - 4 * mRectInset,
                    Region.Op.DIFFERENCE);
        else {
            canvas.clipOutRect(4 * mRectInset, 4 * mRectInset,
                    mClipRectRight - 4 * mRectInset,
                    mClipRectBottom - 4 * mRectInset);
        }


        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(mColumnOne, mRowTwo);

        mPath.rewind();
        mPath.addCircle(mCircleRadius, mClipRectBottom - mCircleRadius,
                mCircleRadius, Path.Direction.CCW);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        } else {
            canvas.clipOutPath(mPath);
        }
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(mColumnnTwo, mRowTwo);
        canvas.clipRect(mClipRectLeft, mClipRectTop,
                mClipRectRight - mSmallRectOffset,
                mClipRectBottom - mSmallRectOffset);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipRect(mClipRectLeft + mSmallRectOffset,
                    mClipRectTop + mSmallRectOffset, mClipRectRight,
                    mClipRectBottom, Region.Op.INTERSECT);
        } else {
            canvas.clipRect(mClipRectLeft + mSmallRectOffset,
                    mClipRectTop + mSmallRectOffset, mClipRectRight,
                    mClipRectBottom);
        }

        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(mColumnOne, mRowThree);
        mPath.rewind();
        mPath.addCircle(mClipRectLeft + mRectInset + mCircleRadius,
                mClipRectTop + mCircleRadius + mRectInset,
                mCircleRadius, Path.Direction.CCW);
        mPath.addRect(mClipRectRight / 2 - mCircleRadius,
                mClipRectTop + mCircleRadius + mRectInset,
                mClipRectRight / 2 + mCircleRadius,
                mClipRectBottom - mRectInset, Path.Direction.CCW);
        canvas.clipPath(mPath);
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(mColumnnTwo, mRowThree);
        mPath.rewind();
        mPath.addRoundRect(mRectF, (float) mClipRectRight / 4,
                (float) mClipRectRight / 4, Path.Direction.CCW);
        canvas.clipPath(mPath);
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();

        canvas.translate(mColumnOne, mRowFour);
        canvas.clipRect(2 * mRectInset, 2 * mRectInset,
                mClipRectRight - 2 * mRectInset,
                mClipRectBottom - 2 * mRectInset);
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        mPaint.setColor(Color.WHITE);

        mPaint.setTextAlign(Paint.Align.LEFT);

        canvas.translate(mColumnnTwo, mTextRow);

        canvas.drawText(
                getContext().getString(R.string.translated1), 0, 0, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        canvas.translate(mColumnnTwo, mTextRow);

        canvas.skew(0.2f, 0.3f);
        canvas.drawText(
                getContext().getString(R.string.skewed1), 0, 0, mPaint);
        canvas.restore();
    }
}

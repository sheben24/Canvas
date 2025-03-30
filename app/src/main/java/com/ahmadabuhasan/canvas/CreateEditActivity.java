package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ahmad Abu Hasan on 18/10/2020.
 */

public class CreateEditActivity extends AppCompatActivity {

    private static final int OFFSET = 65;

    private static final int MULTIPLIER = 500;

    private Canvas mCanvas;

    private Paint mPaint = new Paint();

    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

    private ImageView mImageView;

    private Rect mRect = new Rect();

    private int mOffset = OFFSET;

    private Rect mBounds = new Rect();

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
        setTitle("EDIT Create Canvas objects");

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground1, null);

        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle1, null);

        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent1, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark1, null)
        );
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.myimageview1);

    }

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;

        if (mOffset == OFFSET) {

            Bitmap mBitmap = Bitmap.createBitmap(
                    vWidth, vHeight, Bitmap.Config.ARGB_8888);

            mImageView.setImageBitmap(mBitmap);

            mCanvas = new Canvas(mBitmap);

            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping1), 100, 100, mPaintText);

            mOffset += OFFSET;
        } else {

            if (mOffset < halfWidth && mOffset < halfHeight) {

                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);
                mRect.set(
                        mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);

                mOffset += OFFSET;
            } else {
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(
                        halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.done1);

                mPaintText.getTextBounds(text, 0, text.length(), mBounds);

                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        view.invalidate();
    }
}
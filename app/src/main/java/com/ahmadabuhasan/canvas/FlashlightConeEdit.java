package com.ahmadabuhasan.canvas;

/**
 * Created by Ahmad Abu Hasan on 18/10/2020.
 */

public class FlashlightConeEdit {

    private int mX;
    private int mY;
    private int mRadius;

    public FlashlightConeEdit(int viewWidth, int viewHeight) {
        mX = viewWidth / 2;
        mY = viewHeight / 2;

        mRadius = ((viewWidth <= viewHeight) ? mX / 6 : mY / 6);
    }

    public void update(int newX, int newY) {
        mX = newX;
        mY = newY;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getRadius() {
        return mRadius;
    }
}
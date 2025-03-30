package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

/**
 * Created by Ahmad Abu Hasan on 18/10/2020.
 */

public class SurfaceEditActivity extends AppCompatActivity {

    private GameViewEdit mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EDIT SurfaceView objects");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mGameView = new GameViewEdit(this);

        mGameView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(mGameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }
}
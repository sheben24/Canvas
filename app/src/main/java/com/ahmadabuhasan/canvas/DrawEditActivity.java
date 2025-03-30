package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

/**
 * Created by Ahmad Abu Hasan on 18/10/2020.
 */

public class DrawEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_edit);
        setTitle("EDIT Draw on Canvas objects");

        MyCanvasViewEdit myCanvasView;

        myCanvasView = new MyCanvasViewEdit(this);

        myCanvasView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(myCanvasView);
    }
}
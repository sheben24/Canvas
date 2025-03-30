package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ClippingEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ClippedViewEdit(this));
        setTitle("EDIT Apply clipping to Canvas objects");
    }
}
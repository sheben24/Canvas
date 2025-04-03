package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_ori);
        LinearLayout linearLayout = new LinearLayout(this);

        Button color = new Button(this);
        MyCanvasView myCanvasView = new MyCanvasView(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(color);
        linearLayout.addView(myCanvasView);

        setContentView(linearLayout);

        new ColorPickerDialog.Builder(this)
                .setTitle("Color picker")
                .setPositiveButton("Confirm",
                        (ColorEnvelopeListener) (envelope, fromUser) -> myCanvasView.setColor(envelope.getColor()))
                .setNegativeButton("Cancel",
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(false)
                .attachBrightnessSlideBar(true)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.draw, menu);
        return true;
    }
}
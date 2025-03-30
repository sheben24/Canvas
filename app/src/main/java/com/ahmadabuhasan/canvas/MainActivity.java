package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

/**
 * CanvasExample shows how to create a custom view and draw on its canvas.
 */


public class DrawOriActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_ori);
        LinearLayout linearLayout = new LinearLayout(this);
        MyCanvasView myCanvasView;
        EditText r = new EditText(this);
        EditText g = new EditText(this);
        EditText b = new EditText(this);
        Button color = new Button(this);
        myCanvasView = new MyCanvasView(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);
        linearLayout.addView(r);
        linearLayout.addView(g);
        linearLayout.addView(b);
        linearLayout.addView(color);
        linearLayout.addView(myCanvasView);
        r.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                MyCanvasView.r = Integer.parseInt(String.valueOf(r.getText()));
                MyCanvasView.g = Integer.parseInt(String.valueOf(r.getText()));
                MyCanvasView.b = Integer.parseInt(String.valueOf(r.getText()));
                color.setBackgroundColor(Color.rgb(MyCanvasView.r,MyCanvasView.g,MyCanvasView.b));
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.draw, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_draw) {
            startActivity(new Intent(this, DrawEditActivity.class));
            Toast.makeText(DrawOriActivity.this, "Edit Draw on Canvas objects", 1000).show();
        }
        return true;
    }
}
/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ahmadabuhasan.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.locks.Lock;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

/**
 * This app demonstrates how to use a SurfaceView to render an image from a
 * separate thread.
 * In addition, it shows how you can use clipping for animation,
 * and implements a basic game loop. This is not intended as a production
 * quality app. Rather, it demonstrates the basics
 * of these techniques, so you can dive deeper on your own.
 * <p>
 * Note that SurfaceView offers trade-offs you must consider:
 * <p>
 * * Offers a lover level drawing surface with more control without the need
 * for learning OpenGL or the NDK.
 * * You can draw on it same as a canvas.
 * * Draw from a separate thread, not the UI thread.
 * * Does not have built-in hardware acceleration, e.g. for transformations.
 * Monitor performance carefully, especially if you are doing animations.
 * <p>
 * Game play:
 * <p>
 * The user is presented with a dark surface with a white circle.
 * This represents a wall shone at with a flashlight cone. Touching the
 * surface hides an android image. The light cone then follows continuous
 * motion. If the image of an android is discovered, the screen lights up and
 * the word "WIN!" appears. To restart lift finger and touch screen again.
 * <p>
 * The following limitations are imposed to keep the code focused.
 * <p>
 * * No startup screen or any other functionality other than game play.
 * * No saving of state, game, or user data.
 * * No acrobatics to handle edge cases.
 */

public class SurfaceOriActivity extends AppCompatActivity {

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ORI SurfaceView objects");

        // Lock orientation into landscape.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Create a GameView and bind it to this activity.
        // You don't need a ViewGroup to fill the screen, because the system
        // has a FrameLayout to which this will be added.
        mGameView = new GameView(this);
        // Android 4.1 and higher simple way to request fullscreen.
        mGameView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(mGameView);
    }

    /**
     * Pauses game when activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

    /**
     * Resumes game when activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.surface, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_surface) {
            startActivity(new Intent(this, SurfaceEditActivity.class));
            Toast.makeText(SurfaceOriActivity.this, "Edit SurfaceView objects", 1000).show();
        }
        return true;
    }
}
package com.example.jingle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jingle.ui.MyPresentation;
import com.example.jingle.ui.MySplashTV;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (displayManager != null) {
            Toast.makeText(this, "sdfsdf", Toast.LENGTH_SHORT).show();
            Display[] dssda = displayManager.getDisplays();
            Log.i("sdfdsa",String.valueOf((dssda[1].getDisplayId())));
            Display display = displayManager.getDisplay(dssda[1].getDisplayId());
            showPresentation(display);
            displayManager.registerDisplayListener(new DisplayManager.DisplayListener() {
                @Override
                public void onDisplayAdded(int displayId) {
                    Log.d("MainActivity", "Display added: " + displayId);
                    Display display = displayManager.getDisplay(1);
                    if (display != null && display.getDisplayId() != Display.DEFAULT_DISPLAY) {
                        showPresentation(display);
                    }
                }

                @Override
                public void onDisplayChanged(int displayId) {
                    Log.d("MainActivity", "Display changed: " + displayId);
                }

                @Override
                public void onDisplayRemoved(int displayId) {
                    Log.d("MainActivity", "Display removed: " + displayId);
                    if (presentation != null && presentation.getDisplay().getDisplayId() == displayId) {
                        presentation.dismiss();
                        presentation = null;
                    }
                }
            }, null);
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, FirstPage.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }



    private void showPresentation(Display display) {
        presentation = new MySplashTV(this, display, this);
        presentation.show();
    }
    private DisplayManager displayManager;
    private MySplashTV presentation;
}
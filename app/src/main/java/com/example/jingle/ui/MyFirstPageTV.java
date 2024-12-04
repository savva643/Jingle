package com.example.jingle.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.widget.ImageView;

import com.example.jingle.R;


public class MyFirstPageTV extends Presentation {
    Activity activityi;
    public MyFirstPageTV(Context outerContext, Display display, Activity activity) {
        super(outerContext, display);
        activityi = activity;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_firsta_start);
        ImageView ime = findViewById(R.id.imageView);
        ime.setImageDrawable(activityi.getDrawable(R.mipmap.logo_jingle_foreground));
    }

}
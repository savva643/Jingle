package com.example.jingle.ui;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jingle.AppAdapter;
import com.example.jingle.R;

import java.util.List;

public class MyPresentation extends Presentation {
    Activity activityi;
    public MyPresentation(Context outerContext, Display display, Activity activity) {
        super(outerContext, display);
        activityi = activity;
    }
    private RecyclerView appDrawer;
    private PackageManager packageManager;
    private List<ResolveInfo> apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tv);
        appDrawer = findViewById(R.id.appDrawer);
        packageManager = activityi.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);


        apps = packageManager.queryIntentActivities(intent, 0);
        TextView sadsad = findViewById(R.id.textView4);
        sadsad.setText(String.valueOf(apps.size()));
        ResolveInfo app = apps.get(0);
        Log.i("dsaadsds", String.valueOf(app.activityInfo.packageName));
        LinearLayoutManager dsad = new LinearLayoutManager(getContext());
        dsad.setOrientation(RecyclerView.HORIZONTAL);
        appDrawer.setLayoutManager(dsad);
        appDrawer.setAdapter(new AppAdapter(apps, packageManager));
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
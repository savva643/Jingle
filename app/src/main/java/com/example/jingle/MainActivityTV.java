package com.example.jingle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivityTV extends AppCompatActivity {
    private RecyclerView appDrawer;
    private PackageManager packageManager;
    private List<ResolveInfo> apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tv);
        appDrawer = findViewById(R.id.appDrawer);
        packageManager = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        apps = packageManager.queryIntentActivities(intent, 0);
        Log.i("dsaadsds", String.valueOf(apps.size()));
        TextView sadsad = findViewById(R.id.textView4);
        sadsad.setText(String.valueOf(apps.size()));
        LinearLayoutManager dsad = new LinearLayoutManager(this);
        dsad.setOrientation(RecyclerView.HORIZONTAL);
        appDrawer.setLayoutManager(new LinearLayoutManager(this));
        appDrawer.setAdapter(new AppAdapter(apps, packageManager, this));
    }
}
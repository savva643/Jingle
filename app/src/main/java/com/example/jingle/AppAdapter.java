package com.example.jingle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private List<ResolveInfo> apps;
    private PackageManager packageManager;

    public AppAdapter(List<ResolveInfo> apps, PackageManager packageManager) {
        this.apps = apps;
        this.packageManager = packageManager;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        ResolveInfo app = apps.get(position);
        holder.appIcon.setImageDrawable(app.activityInfo.loadIcon(packageManager));
        Log.i("dfsdf",String.valueOf(position));
        if(position==0){
            Log.i("dfsdf","iouii");
            holder.motionLayout.transitionToEnd();
        }
        holder.itemView.setOnClickListener(v -> {
            Intent launchIntent = packageManager.getLaunchIntentForPackage(app.activityInfo.packageName);
            if (launchIntent != null) {
                v.getContext().startActivity(launchIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {

        ImageView appIcon;
        MotionLayout motionLayout;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            motionLayout = itemView.findViewById(R.id.mtss);
        }
    }
}


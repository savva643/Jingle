package com.example.jingle;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private List<ResolveInfo> apps;
    private PackageManager packageManager;
    private Activity ac;
    public AppAdapter(List<ResolveInfo> apps, PackageManager packageManager, Activity acas) {
        this.apps = apps;
        this.packageManager = packageManager;
        this.ac = acas;
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
            LinearLayout.LayoutParams paramsi = new LinearLayout.LayoutParams((int) (245 * ac.getResources().getDisplayMetrics().density + 0.5f),
                    (int) (152 * ac.getResources().getDisplayMetrics().density + 0.5f));
            holder.dsdas.setLayoutParams(paramsi);
            holder.motionLayout.setLayoutParams(paramsi);
        }else{
            LinearLayout.LayoutParams paramsi = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsi.setMargins(0,12,0,0);
            holder.dsdas.setLayoutParams(paramsi);
            holder.motionLayout.setLayoutParams(paramsi);
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
        LinearLayout dsdas;
        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            motionLayout = itemView.findViewById(R.id.mtss);
            dsdas = itemView.findViewById(R.id.fghhfg);
        }
    }
}


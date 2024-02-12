package com.example.jingle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter< JSONObject> {
    int listLayout;
    ArrayList<JSONObject> list;
    Context context;
FragmentManager fr;
Activity ac;
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
            imageView.setVisibility(View.VISIBLE);
        }

    }

    public class Hoverlog extends AsyncTask<Void, Void, Bitmap> {

        private ImageView imageView;

        public Hoverlog(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            do{
                Log.i("ddeseseseseseseseseseseseseses","ddeseseseseseseseseseseseseses ");
                if(!imageView.isHovered()){
                    Log.i("fesfsefsefesf","fesfdsghjgj ");
                    fr.popBackStack();
                    ac.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                    imageView.setEnabled(true);
                        }
                    });
                    break;
                }
            }while (!imageView.isHovered());

            return null;
        }


    }

    public UserAdapter(Context context, int listLayout, int field , ArrayList< JSONObject> list, FragmentManager fr, Activity ac){
        super(context,listLayout,field,list);
        this.context=context;
        this.listLayout=listLayout;
        this.list=list;
        this.fr=fr;
        this.ac=ac;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(listLayout,parent,false);
        TextView name = itemView.findViewById(R.id.name);
        TextView email = itemView.findViewById(R.id.mes);
        ImageView image = itemView.findViewById(R.id.imageView191);
        image.setClickable(true);


        try {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
 /* image.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        image.setClickable(false);
        Bundle bundle = new Bundle();
        try {
            bundle.putString("id", list.get(position).getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        previewmes an = new previewmes();
        an.setArguments(bundle);
        fr.beginTransaction().setCustomAnimations(
                R.anim.fade_inv,  // enter
                R.anim.fade_outv,  // exit
                R.anim.fade_inv,   // popEnter
                R.anim.fade_outv  // popExit
        ).replace(R.id.nav_host_fragment_activity_main1, an).setReorderingAllowed(true).addToBackStack(null).commit();
        new Hoverlog(image).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return false;
    }
});  */
            name.setText(list.get(position).getString("name"));
            email.setText(list.get(position).getString("message"));
            String urla = list.get(position).getString("img");
            new ImageLoadTask(urla, image).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return itemView;
    }
}

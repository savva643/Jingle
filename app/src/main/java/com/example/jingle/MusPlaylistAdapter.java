package com.example.jingle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MusPlaylistAdapter extends ArrayAdapter< JSONObject> {
    int listLayout;
    ArrayList<JSONObject> list;
    Context context;
FragmentManager fr;
Activity ac;
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private GifImageView imageView;

        public ImageLoadTask(String url, GifImageView imageView) {
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

    public MusPlaylistAdapter(Context context, int listLayout, int field , ArrayList< JSONObject> list, FragmentManager fr, Activity ac){
        super(context,listLayout,field,list);
        this.context=context;
        this.listLayout=listLayout;
        this.list=list;
        this.fr=fr;
        this.ac=ac;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.playlistmus,parent,false);
        TextView name = itemView.findViewById(R.id.name);
        GifImageView image = itemView.findViewById(R.id.imageView190);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currValue = sp.getString("Theme","-1");

        try {

            if(list.get(position).getString("id").equals("-1")) {
                name.setText(R.string.newplaylist);
                int resID = ac.getResources().getIdentifier("addgif", "drawable",  ac.getPackageName());
                image.setImageResource(resID);
            }else if(list.get(position).getString("id").equals("0")){
                name.setText(R.string.loveplaylist);
                int resID = ac.getResources().getIdentifier("loveplaylist", "drawable",  ac.getPackageName());
                image.setImageResource(resID);
            }else{
                String urla = list.get(position).getString("img");
                new ImageLoadTask(urla, image).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                name.setText(list.get(position).getString("name"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return itemView;
    }
}

package com.example.jingle.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jingle.R;
import com.example.jingle.UserAdapter;
import com.example.jingle.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.blurry.Blurry;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public class ImageLoadTaskiback extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;
        public ImageLoadTaskiback(String url, ImageView imageView) {
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

            // from Bitmap
            Blurry.with(getContext()).from(result).into(imageView);
        }
    }


    public class ImageLoadTaski extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;
        private CardView cs;
        public ImageLoadTaski(String url, ImageView imageView, CardView cs) {
            this.url = url;
            this.imageView = imageView;
            this.cs=cs;
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
            cs.setVisibility(View.VISIBLE);
        }

    }

    private class MyAsync extends AsyncTask<Void, Integer, Void> {
        int duration = 0;
        int current = 0;

        private String url;


        @Override
        protected Void doInBackground(Void... params) {
            ImageView imu = getView().findViewById(R.id.imageView191);
            mProgressBari = getView().findViewById(R.id.seekBar);
            hg = 0;

            do {

                if (isCancelled()) break;
                if (ms.isCancelled()) {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                    break;
                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });


                try{
                    current = mediaPlayer.getCurrentPosition();
                    duration = mediaPlayer.getDuration();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try{
                    current = mediaPlayer.getCurrentPosition();
                    duration = mediaPlayer.getDuration();
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(current);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(current);
                    long seconds2 = TimeUnit.MINUTES.toSeconds(minutes);
                    String seco;
                    String seco6;
                    long minutes6 = TimeUnit.MILLISECONDS.toMinutes(duration);
                    long seconds6 = TimeUnit.MILLISECONDS.toSeconds(duration);
                    long seconds26 = TimeUnit.MINUTES.toSeconds(minutes6);

                    if (seconds != seconds3) {
                        if (seconds - seconds2 < 10) {
                            seco = "0" + String.valueOf(seconds - seconds2);
                        } else {
                            seco = String.valueOf(seconds - seconds2);
                        }
                        time = String.valueOf(minutes) + ":" + seco;
                        seconds3 = TimeUnit.MILLISECONDS.toSeconds(current);

                        if (seconds6 - seconds26 < 10) {
                            seco6 = "0" + String.valueOf(seconds6 - seconds26);
                        } else {
                            seco6 = String.valueOf(seconds6 - seconds26);
                        }
                        time6 = String.valueOf(minutes6) + ":" + seco6;
                        seconds36 = TimeUnit.MILLISECONDS.toSeconds(duration);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("durationуавцыа - " + duration + " currentауыаау- "
                        + current);
                try {
                    publishProgress((int) (current * 100 / duration));
                    if (mProgressBari.getProgress() >= 100) {
                        ImageButton play2 =  getView().findViewById(R.id.imageButton6);
                        play2.setImageResource(R.drawable.ic_baseline_refresh_24);
                        mediaPlayer.start();
                        play2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ms = new MyAsync();
                                ms.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                play2.setImageResource(R.drawable.ic_baseline_pause_24);
                                play2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(mediaPlayer.isPlaying()){
                                            mediaPlayer.pause();
                                            play2.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                                        }else{
                                            mediaPlayer.start();
                                            play2.setImageResource(R.drawable.ic_baseline_pause_24);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    }

                } catch (Exception e) {
                }
            } while (mProgressBari.getProgress() <= 100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            System.out.println(values[0]);
            mProgressBari.setProgress(values[0]);
            TextView ti = getView().findViewById(R.id.textView19);
            TextView tis = getView().findViewById(R.id.textView20);
            if(mediaPlayer != null) {
                if(hg != 1) {
                    if(!time6.equals("null")) {
                        tis.setText(time6);

                    }else{
                        duration = mediaPlayer.getDuration();
                        String seco6;
                        long minutes6 = TimeUnit.MILLISECONDS.toMinutes(duration);
                        long seconds6 = TimeUnit.MILLISECONDS.toSeconds(duration);
                        long seconds26 = TimeUnit.MINUTES.toSeconds(minutes6);
                        if (seconds6 - seconds26 < 10) {
                            seco6 = "0" + String.valueOf(seconds6 - seconds26);
                        } else {
                            seco6 = String.valueOf(seconds6 - seconds26);
                        }
                        time6 = String.valueOf(minutes6) + ":" + seco6;
                        seconds36 = TimeUnit.MILLISECONDS.toSeconds(duration);
                        tis.setText(time6);
                    }
                    hg = 1;
                }
            }
            ti.setText(time);
        }
    }
    public SeekBar mProgressBari;
    MyAsync ms;
    public int eg = 0;
    public int hg = 0;
    long seconds3 = 0;
    long seconds36 = 0;
    public String time6 = "null";
    public String time;
    public int type = 2;
    public static String play = "1";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }
    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){
        ArrayList< JSONObject> aList = new ArrayList<JSONObject>();
        try {
            if(jsonArray!= null){
                for(int i = 0; i< jsonArray.length();i++){
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        }catch (JSONException js){
            js.printStackTrace();
        }
        return aList;
    }
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet;
    public void load(){
        SwipeRefreshLayout mSwipeRefreshLayout = getView().findViewById(R.id.swipemus);
        ProgressBar progressBar = getView().findViewById(R.id.progressBar6);
        bottom_sheet = getView().findViewById(R.id.bottom_sheeti);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String token = preferences.getString("token", "");
        ListView listView = getView().findViewById(R.id.listcomment);
        CardView cs = getView().findViewById(R.id.viopie);
        String url = "https://kompot.fun/gettopmusic";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArraya = null;

                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            mSwipeRefreshLayout.setRefreshing(false);
                            jsonArraya = new JSONArray(response);
                            JSONArray jsonArray = jsonArraya.getJSONArray(0);

                            listView.setVisibility(View.VISIBLE);
                            ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                            ListAdapter adapter = null;
                            try {
                                adapter = new UserAdapter(getActivity().getApplicationContext(), R.layout.row, R.id.name, listItems, getParentFragmentManager(), getActivity());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @SuppressLint("ResourceType")
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                    if (ContextCompat.checkSelfPermission(getActivity(),
                                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.RECORD_AUDIO},
                                                1);

                                    }else {
                                        try {
                                            try {
                                                if (mediaPlayer != null) {
                                                    ms.cancel(true);
                                                    mediaPlayer.stop();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            JSONObject jsonObject = new JSONObject(listView.getItemAtPosition(position).toString());
                                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                            mSwipeRefreshLayout.setEnabled(false);
                                            TextView name = getView().findViewById(R.id.textView14);
                                            ImageButton play2 = getView().findViewById(R.id.imageButton6);

                                            ImageButton love = getView().findViewById(R.id.imageButton10);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                            String token = preferences.getString("token", "");
                                            love.setImageResource(R.drawable.baseline_favorite_border_24);

                                                if (jsonObject.getString("doi").equals("1")) {
                                                    love.setImageResource(R.drawable.baseline_favorite_24);
                                                    type = 1;
                                                } else if (jsonObject.getString("doi").equals("0")) {
                                                    love.setImageResource(R.mipmap.unfavoriteyes_foreground);
                                                    type = 0;
                                                }


                                            play2 = getView().findViewById(R.id.imageButton6);
                                            play2.setImageResource(R.drawable.ic_baseline_pause_24);

                                            ImageButton finalPlay = play2;
                                            play2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    eg = 1;
                                                    if (mediaPlayer.isPlaying()) {
                                                        mediaPlayer.pause();
                                                        play = "0";
                                                        finalPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);

                                                    } else {
                                                        play = "1";
                                                        mediaPlayer.start();
                                                        finalPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                                                    }
                                                }
                                            });
                                            TextView user = getView().findViewById(R.id.textView15);
                                            ImageView img = getView().findViewById(R.id.imageView191);
                                            ImageView elir = getView().findViewById(R.id.imageView16);
                                            ImageView imgbacki = getView().findViewById(R.id.imageView191ds);
                                            SeekBar mProgressBar = getView().findViewById(R.id.seekBar);
                                            mProgressBar.setProgress(0);
                                            mProgressBar.setMax(100);
                                            name.setText(jsonObject.getString("name"));
                                            user.setText(jsonObject.getString("message"));
                                            String urla = jsonObject.getString("img");
                                            if (jsonObject.getString("elir").equals("1")) {
                                                elir.setVisibility(View.VISIBLE);
                                            } else {
                                                elir.setVisibility(View.INVISIBLE);
                                            }
                                            TextView tis = getView().findViewById(R.id.textView20);
                                            if (mediaPlayer != null) {
                                                tis.setText(String.valueOf(mediaPlayer.getDuration()));
                                            }


                                            Uri myUri = Uri.parse(jsonObject.getString("url"));
                                            try {
                                                mediaPlayer.reset();
                                                mediaPlayer.setDataSource(getActivity(), myUri);
                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                try {
                                                    mediaPlayer.prepareAsync(); //don't use prepareAsync for mp3 playback
                                                } catch (IllegalStateException e) {
                                                    e.printStackTrace();
                                                }
                                                try {
                                                    mediaPlayer.start();
                                                    new ImageLoadTaski(urla, img, cs).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                    new ImageLoadTaskiback(urla, imgbacki).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                    ms = new MyAsync();
                                                    ms.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                } catch (IllegalStateException e) {
                                                    e.printStackTrace();
                                                }

                                                mProgressBar.setClickable(true);
                                                mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                    @Override
                                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                        if (fromUser) {
                                                            if (mediaPlayer != null) {
                                                                try {
                                                                    int dira = mediaPlayer.getDuration();
                                                                    mediaPlayer.seekTo(progress * dira / 100);
                                                                } catch (IllegalStateException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                                    }

                                                    @Override
                                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                                    }
                                                });
                                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        Toast.makeText(getContext(), "gyuguuyhj", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                String token = preferences.getString("token", "");
                String line1 = token.replace("\"", "");
                params.put("token", line1);
                return params;
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
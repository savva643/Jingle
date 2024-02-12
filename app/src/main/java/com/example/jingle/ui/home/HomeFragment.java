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
import android.widget.AbsListView;
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
import androidx.annotation.Nullable;
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
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
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


    PlayerView video = null;
    ExoPlayer player = null;
    TextView ti;
    TextView tis;
    public void again(){

    }
    public void intvid(){
        ti = getView().findViewById(R.id.textView19);
        tis = getView().findViewById(R.id.textView20);
        video = getView().findViewById(R.id.videoView2);
        player = new ExoPlayer.Builder(getContext()).build();
        video.setPlayer(player);

    }
    public void relis(){
        if (player != null){
            player.release();
            player = null;
        }
    }
    public void startnex(String gg){
        relis();
        intvid();
        if(hal != null) {
            ispg = false;
        }
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(gg));
        player.setMediaItem(mediaItem);
        player.prepare();

        player.play();
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
                if(playbackState == Player.STATE_READY){
                     updateSeekBar();
                 }else{
                     Toast.makeText(getContext(), "den", Toast.LENGTH_SHORT).show();
                 }
    }
});
    }



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
    Thread hal;
    boolean ispg = true;
    private void updateSeekBar() {
        ispg = true;
        final long[] duration = {player.getDuration()};
        final int[] prgcur = {0};
        mProgressBari = getView().findViewById(R.id.seekBar);
        mProgressBari.setMax(1000);
        final long[] current = {0};
        hg = 0;
        hal = new Thread() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prgcur[0] = mProgressBari.getProgress();
                    }
                });
                while (prgcur[0] <= 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            current[0] = player.getCurrentPosition();
                        }
                    });

                    if (!ispg) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        if (player.isPlaying()) {
                            player.pause();
                        }
                            }
                        });
                        break;
                    }
                    try {


                        long minutes = TimeUnit.MILLISECONDS.toMinutes(current[0]);
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(current[0]);
                        long seconds2 = TimeUnit.MINUTES.toSeconds(minutes);
                        String seco;
                        String seco6;
                        long minutes6 = TimeUnit.MILLISECONDS.toMinutes(duration[0]);
                        long seconds6 = TimeUnit.MILLISECONDS.toSeconds(duration[0]);
                        long seconds26 = TimeUnit.MINUTES.toSeconds(minutes6);

                        if (seconds != seconds3) {
                            if (seconds - seconds2 < 10) {
                                seco = "0" + String.valueOf(seconds - seconds2);
                            } else {
                                seco = String.valueOf(seconds - seconds2);
                            }
                            time = String.valueOf(minutes) + ":" + seco;
                            seconds3 = TimeUnit.MILLISECONDS.toSeconds(current[0]);

                            if (seconds6 - seconds26 < 10) {
                                seco6 = "0" + String.valueOf(seconds6 - seconds26);
                            } else {
                                seco6 = String.valueOf(seconds6 - seconds26);
                            }
                            time6 = String.valueOf(minutes6) + ":" + seco6;
                            seconds36 = TimeUnit.MILLISECONDS.toSeconds(duration[0]);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        mProgressBari.setProgress((int) (current[0] * 1000 / duration[0]));
                            }
                        });
                        if (player != null) {
                            if (hg != 1) {
                                if (!time6.equals("null")) {
                                    tis.setText(time6);

                                } else {
                                    String seco6;
                                    long minutes6 = TimeUnit.MILLISECONDS.toMinutes(duration[0]);
                                    long seconds6 = TimeUnit.MILLISECONDS.toSeconds(duration[0]);
                                    long seconds26 = TimeUnit.MINUTES.toSeconds(minutes6);
                                    if (seconds6 - seconds26 < 10) {
                                        seco6 = "0" + String.valueOf(seconds6 - seconds26);
                                    } else {
                                        seco6 = String.valueOf(seconds6 - seconds26);
                                    }
                                    time6 = String.valueOf(minutes6) + ":" + seco6;
                                    seconds36 = TimeUnit.MILLISECONDS.toSeconds(duration[0]);
                                    tis.setText(time6);
                                }
                                hg = 1;
                            }
                        }
                        ti.setText(time);
                        if (prgcur[0] >= 100) {

                            break;
                        }

                    } catch (Exception ignored) {
                    }

                }
            }

        };
        hal.start();
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


                    do {


                            if (isCancelled()) break;
                            if (ms.isCancelled()) {
                                if (player.isPlaying()) {
                                    player.pause();
                                }
                                break;
                            }
                            try {

                                current = (int) player.getCurrentPosition();
                                duration = (int) player.getDuration();
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

            if(player != null) {
                if(hg != 1) {
                    if(!time6.equals("null")) {
                        tis.setText(time6);

                    }else{
                        duration = (int) player.getDuration();
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
                                                if (player != null) {
                                                    ms.cancel(true);
                                                    player.stop();
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
                                            love.setImageResource(R.mipmap.loveno_foreground);

                                                if (jsonObject.getString("doi").equals("1")) {
                                                    love.setImageResource(R.mipmap.loveyes_foreground);
                                                    type = 1;
                                                } else if (jsonObject.getString("doi").equals("0")) {
                                                    love.setImageResource(R.mipmap.unloveyes_foreground);
                                                    type = 0;
                                                }


                                            play2 = getView().findViewById(R.id.imageButton6);
                                            play2.setImageResource(R.mipmap.pause_foreground);

                                            ImageButton finalPlay = play2;
                                            play2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    eg = 1;
                                                    if (player.isPlaying()) {
                                                        player.pause();
                                                        play = "0";
                                                        finalPlay.setImageResource(R.mipmap.play_foreground);

                                                    } else {
                                                        play = "1";
                                                        player.play();
                                                        finalPlay.setImageResource(R.mipmap.pause_foreground);
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
                                            if (player != null) {
                                                tis.setText(String.valueOf(player.getDuration()));
                                            }
                                            startnex(jsonObject.getString("url"));

                                            try {

                                                new ImageLoadTaski(urla, img, cs).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                new ImageLoadTaskiback(urla, imgbacki).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            } catch (IllegalStateException e) {
                                                e.printStackTrace();
                                            }

                                            mProgressBar.setClickable(true);
                                            mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                @Override
                                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                    if (fromUser) {
                                                        if (player != null) {
                                                            try {
                                                                int dira = (int) player.getDuration();
                                                                player.seekTo(progress * dira / 1000);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getView().findViewById(R.id.listcomment);
        listView.setClickable(true);
        load();
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        SwipeRefreshLayout mSwipeRefreshLayout = getView().findViewById(R.id.swipemus);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null ) {
                    mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
        if (sheetBehavior.getState() != sheetBehavior.STATE_EXPANDED) {
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (listView.getChildAt(0) != null) {
                        mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                    }
                }
            });
        }else{
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
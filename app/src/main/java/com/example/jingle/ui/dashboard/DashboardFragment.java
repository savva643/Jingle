package com.example.jingle.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jingle.MusPlaylistAdapter;
import com.example.jingle.R;
import com.example.jingle.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
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

    void load(){
        ProgressBar mProgressBar = getView().findViewById(R.id.progressBar22);
        SwipeRefreshLayout mSwipeRefreshLayout = getView().findViewById(R.id.dvjdvx);
        ListView listView = getView().findViewById(R.id.listView2);
        String url = "https://kompot.site/getmusicplaylist";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArraya = null;
                        try {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mSwipeRefreshLayout.setRefreshing(false);
                            jsonArraya = new JSONArray(response);
                            JSONArray jsonArray = jsonArraya.getJSONArray(0);

                            listView.setVisibility(View.VISIBLE);
                            ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                            ListAdapter adapter = null;
                            try {
                                adapter = new MusPlaylistAdapter(getActivity().getApplicationContext(), R.layout.playlistmus, R.id.name, listItems, getParentFragmentManager(), getActivity());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @SuppressLint("ResourceType")
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

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
        SwipeRefreshLayout mSwipeRefreshLayout = getView().findViewById(R.id.dvjdvx);
        ListView listView = getView().findViewById(R.id.listView2);
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
                if (listView.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
        load();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
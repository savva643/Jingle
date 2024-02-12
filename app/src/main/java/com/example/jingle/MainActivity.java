package com.example.jingle;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.jingle.ui.dashboard.DashboardFragment;
import com.example.jingle.ui.home.HomeFragment;
import com.example.jingle.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jingle.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(AppCompatActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment frag_new = null;
            if (position==0) frag_new= new HomeFragment();
            if (position==1) frag_new= new DashboardFragment();
            if (position==2) frag_new= new NotificationsFragment();
            return frag_new;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ViewPager2 viewPager = findViewById(R.id.viewpager);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter((AppCompatActivity)this);
        viewPager.setAdapter(pagerAdapter);
    }

}
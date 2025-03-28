package com.example.soccer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.soccer.adapter.PageAdapter;
import com.example.soccer.model.Match;
import com.example.soccer.model.Player;
import com.example.soccer.model.SoccerEntity;
import com.example.soccer.model.Team;
import com.example.soccer.repository.Repository;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //variables for UI components:
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private PageAdapter adapter;
    private TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Initializing views:");

        initViews();
        Log.d("MainActivity", "views initialized");

        Log.d("MainActivity", "addOnTabSelectedListener():");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        Log.d("MainActivity", "registerOnPageChangeCallback():");
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    public void initViews(){

        tabLayout = findViewById(R.id.tabLayout);
        tvHeader = findViewById(R.id.header);

        viewPager2 =  findViewById(R.id.view_pager);
        adapter = new PageAdapter(this);
        viewPager2.setAdapter(adapter);
    }

}
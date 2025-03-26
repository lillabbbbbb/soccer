package com.example.soccer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.soccer.adapter.MyViewPagerAdapter;
import com.example.soccer.model.Match;
import com.example.soccer.model.Player;
import com.example.soccer.model.Team;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //variables for UI components:
    TabLayout tabLayout;
    ViewPager viewPager;
    MyViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //connect variables to UI components


        //get test data from a DataProvider object
        DataProvider dataProvider = new DataProvider();
        List<Team> teams = dataProvider.createSampleTeams();
        List<Player> players = dataProvider.createSamplePlayers();
        List<Match> matches = dataProvider.createSampleMatches();
    }
}
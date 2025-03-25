package com.example.soccer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fragments.AllFragment;
import fragments.MatchesFragment;
import fragments.PlayersFragment;
import fragments.TeamsFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TeamsFragment();
            case 1:
                return new PlayersFragment();
            case 2:
                return new MatchesFragment();
            case 3:
                return new AllFragment();
            default:
                return new TeamsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

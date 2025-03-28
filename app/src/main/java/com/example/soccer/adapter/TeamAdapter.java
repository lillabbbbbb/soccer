package com.example.soccer.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccer.R;
import com.example.soccer.model.Match;
import com.example.soccer.model.Team;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private final List<Team> teams;
    private final Context context;
    private final OnItemClickListener listener;

    /**
     * Interface for item click events
     */
    public interface OnItemClickListener {
        void onItemClick(Team team, int position);
    }

    public TeamAdapter(Context context, List<Team> teams, OnItemClickListener listener) {
        this.context = context;
        this.teams = teams;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        Log.d("TeamAdapter", "onCreateViewHolder");
        return new TeamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        Team team = teams.get(position);

        // Bind data to views
        holder.tvTeamName.setText(team.getName());
        holder.tvTeamLocation.setText(String.format("%s, %s", team.getCountry(), team.getHome()));
        holder.tvTeamYear.setText(String.valueOf(team.getYear()));
        Log.d("TeamAdapter", "binding data to views");

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(team, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    /**
     * Update the adapter data
     * @param newTeams new list of teams
     */
    public void updateTeams(List<Team> newTeams) {
        teams.clear();
        teams.addAll(newTeams);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for the adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName, tvTeamLocation, tvTeamYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTeamName = itemView.findViewById(R.id.team_name);
            tvTeamLocation = itemView.findViewById(R.id.team_location);
            tvTeamYear = itemView.findViewById(R.id.team_year);
            Log.d("TeamAdapter", "ViewHolder constructor called");
        }
    }
}


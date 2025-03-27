package com.example.soccer.adapter;


import android.content.Context;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_teams, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = teams.get(position);

        // Bind data to views
        holder.tvTeamName.setText(team.getName());
        holder.tvteamLocation.setText(String.format("%d,%d", team.getCountry(), team.getHome()));
        holder.tvTeamYear.setText(team.getYear());

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
    public void updateItems(List<Team> newTeams) {
        teams.clear();
        teams.addAll(newTeams);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for the adapter
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName, tvteamLocation, tvTeamYear;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.homeTeamName);
            tvteamLocation = itemView.findViewById(R.id.awayTeamName);
            tvTeamYear = itemView.findViewById(R.id.score);
        }
    }
}


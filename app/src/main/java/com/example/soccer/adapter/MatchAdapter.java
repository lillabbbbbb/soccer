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

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private final List<Match> matches;
    private final Context context;
    private final OnItemClickListener listener;

    /**
     * Interface for item click events
     */
    public interface OnItemClickListener {
        void onItemClick(Match match, int position);
    }

    public MatchAdapter(Context context, List<Match> matches, OnItemClickListener listener) {
        this.context = context;
        this.matches = matches;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.match_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match match = matches.get(position);

        // Bind data to views
        holder.tvHomeTeam.setText(match.getHomeTeam());
        holder.tvAwayTeam.setText(match.getAwayTeam());
        holder.tvScore.setText(String.valueOf(match.getScore()));
        holder.tvLocation.setText(match.getLocation());
        holder.tvChampionship.setText(match.getChampionship());

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(match, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    /**
     * Update the adapter data
     * @param newMatches new list of matches
     */
    public void updateItems(List<Match> newMatches) {
        matches.clear();
        matches.addAll(newMatches);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for the adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHomeTeam, tvAwayTeam, tvScore, tvLocation, tvChampionship;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHomeTeam = itemView.findViewById(R.id.homeTeamName);
            tvAwayTeam = itemView.findViewById(R.id.awayTeamName);
            tvScore = itemView.findViewById(R.id.score);
            tvLocation = itemView.findViewById(R.id.match_location);
            tvChampionship = itemView.findViewById(R.id.match_championship);
        }
    }
}

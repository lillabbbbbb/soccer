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
import com.example.soccer.model.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private final List<Player> players;
    private final Context context;
    private final OnItemClickListener listener;

    /**
     * Interface for item click events
     */
    public interface OnItemClickListener {
        void onItemClick(Player players, int position);
    }

    public PlayerAdapter(Context context, List<Player> players, OnItemClickListener listener) {
        this.context = context;
        this.players = players;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        Log.d("PlayerAdapter", "onCreateViewHolder");
        return new PlayerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = players.get(position);

        // Bind data to views
        holder.tvPlayerName.setText(player.getName());
        holder.tvPlayerTeam.setText(player.getTeam());
        holder.tvPlayerNumber.setText(String.valueOf(player.getPoints()));
        holder.tvPlayerPosition.setText(player.getPosition());
        holder.tvPlayerAge.setText(String.valueOf(player.getAge()));

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(player, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    /**
     * Update the adapter data
     * @param newPlayers new list of players
     */
    public void updatePlayers(List<Player> newPlayers) {
        players.clear();
        players.addAll(newPlayers);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for the adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerTeam, tvPlayerName, tvPlayerPosition, tvPlayerNumber, tvPlayerAge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerTeam = itemView.findViewById(R.id.playerTeam);
            tvPlayerPosition = itemView.findViewById(R.id.playerPos);
            tvPlayerName = itemView.findViewById(R.id.playerName);
            tvPlayerNumber = itemView.findViewById(R.id.number);
            tvPlayerAge = itemView.findViewById(R.id.playerAge);
            Log.d("PlayerAdapter", "ViewHolder constructor called");
        }
    }
}


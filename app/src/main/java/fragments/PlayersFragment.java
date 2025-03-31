package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soccer.DataProvider;
import com.example.soccer.R;
import com.example.soccer.adapter.PlayerAdapter;
import com.example.soccer.model.Player;
import com.example.soccer.repository.Repository;
import com.example.soccer.repository.PlayerRepository;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class PlayersFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlayerAdapter adapter;
    private PlayerRepository<Player> playerRepository;
    TextView tvEmptyView;
    MaterialCardView filterView, iteratorView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        initViews(view);

        setupPlayerRepository(setupData());

        setupRecyclerView();

        setupButtonListeners(view);

        return view;
    }

    /**
     * Initialize UI components
     */
    private void initViews(View view) {
        tvEmptyView = view.findViewById(R.id.player_tv_empty_view);
        filterView = view.findViewById(R.id.player_card_filter_options);
        iteratorView = view.findViewById(R.id.player_card_iterator_demo);
        recyclerView = view.findViewById(R.id.player_recycler_view);


    }

    /**
     * Set up the RecyclerView and adapter
     */
    private List<Player> setupData(){
        //set up sample data using DataProvider
        //get test data from a DataProvider object
        DataProvider dataProvider = new DataProvider();
        return new ArrayList<>(dataProvider.createSamplePlayers());
    }
    private void setupPlayerRepository(List<Player> players){
        //initialize playerRepository
        playerRepository = new PlayerRepository<>();

        //append players to the playerRepository
        for(int i = 0; i < players.size(); i++){
            playerRepository.addItem(players.get(i));
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the adapter with an empty list initially
        adapter = new PlayerAdapter(
                getContext(),
                new ArrayList<>(),
                (player, position) -> {
                    // Lambda function for item click
                    showPlayerDetails(player);
                }
        );
        recyclerView.setAdapter(adapter);

        // Update adapter with all items
        updateAdapterItems(playerRepository.getAllItems());
    }

    /**
     * Set up click listeners for filter buttons
     * Demonstrates use of lambda functions and generics
     */
    private void setupButtonListeners(View view) {
        // Instantiate all buttons of this fragment
        Button btnFilterportugal, btnFilterFcBarcelona, btnFilterEngland, btnSortName, btnSortFounding, btnForeachIterator, btnCustomIterator;

        // Show all players
        Button btnShowAll = view.findViewById(R.id.player_btn_show_all);
        btnShowAll.setOnClickListener(v -> {
            // Lambda function for click handler
            updateAdapterItems(playerRepository.getAllItems());
            showToast("Showing all players");
        });

        // Filter for Portugal only
        btnFilterportugal = view.findViewById(R.id.player_btn_filter_portugal);
        btnFilterportugal.setOnClickListener(v -> {

            updateAdapterItems(playerRepository.filterByName("Portugal").getAllItems());
            showToast("Filtered: Portugal");
        });

        // Filter for FC Barcelona only
        btnFilterFcBarcelona = view.findViewById(R.id.player_btn_filter_fc_barcelona);
        btnFilterFcBarcelona.setOnClickListener(v -> {
            // Lambda predicate example
            updateAdapterItems(
                    playerRepository.filterByTeam("FC Barcelona").getAllItems()
            );
            showToast("Filtered: FC Barcelona");
        });

        // Filter for tools only
        btnFilterEngland = view.findViewById(R.id.player_btn_filter_england);
        btnFilterEngland.setOnClickListener(v -> {
            updateAdapterItems(
                    playerRepository.filterByCountry("England").getAllItems()
            );
            showToast("Filtered: England");
        });

        // Sort by name (A-Z) (using lambda comparator)
        btnSortName = view.findViewById(R.id.player_btn_sort_name);
        btnSortName.setOnClickListener(v -> {
            List<Player> sortedPlayers = (List<Player>) playerRepository.sortByName();
            updateAdapterItems(sortedPlayers);
            showToast("Sorted by Name (>A-Z)");
        });

        // Sort by age (Ascending) (using lambda comparator)
        btnSortFounding = view.findViewById(R.id.player_btn_sort_age);
        btnSortFounding.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<Player> sortedPlayers = (List<Player>) playerRepository.sortByAge();

            updateAdapterItems(sortedPlayers);
            showToast("Sorted by Age (Descending)");
        });

        // Sort by team (A-Z) (using lambda comparator)
        btnSortFounding = view.findViewById(R.id.player_btn_sort_team);
        btnSortFounding.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<Player> sortedPlayers = (List<Player>) playerRepository.sortByTeam();
            updateAdapterItems(sortedPlayers);

            updateAdapterItems(sortedPlayers);
            showToast("Sorted by Team (A-Z)");
        });



        // Iterator demonstration - using for-each (which uses the Iterator interface)
        btnForeachIterator = view.findViewById(R.id.player_btn_foreach_iterator);
        btnForeachIterator.setOnClickListener(v -> {
            demonstrateForEachIterator();
        });

        // Iterator demonstration - using custom iterator
        btnCustomIterator = view.findViewById(R.id.player_btn_custom_iterator);
        btnCustomIterator.setOnClickListener(v -> {
            demonstrateCustomIterator();
        });
    }

    /**
     * Update the adapter items and manage empty view
     * @param players the new players to display
     */
    private void updateAdapterItems(List<Player> players) {
        adapter.updatePlayers(players);

        // Show/hide empty view
        if (players.isEmpty()) {
            tvEmptyView.setVisibility(android.view.View.VISIBLE);
            recyclerView.setVisibility(android.view.View.GONE);
        } else {
            tvEmptyView.setVisibility(android.view.View.GONE);
            recyclerView.setVisibility(android.view.View.VISIBLE);
        }
    }

    /**
     * Demonstrate using the for-each loop which uses the Iterator interface
     * Shows how implementing Iterable allows for enhanced for loop
     */
    private void demonstrateForEachIterator() {
        StringBuilder result = new StringBuilder("Using for-each loop (Iterator):\n");

        // Using the for-each loop which uses the Iterator interface
        for (Object object : playerRepository.getAllItems()) {
            Player player = (Player) object;
            result.append(" - ").append(player.getName()).append("\n");
        }

        showToast("Check logs for iterator demo results");
        System.out.println(result.toString());
    }

    /**
     * Demonstrate using the custom iterator directly
     * Shows explicit use of an Iterator
     */
    private void demonstrateCustomIterator() {
        StringBuilder result = new StringBuilder("Using custom iterator:\n");

        // Get custom iterator
        Repository<Player>.RepositoryIterator iterator =
                playerRepository.getCustomIterator();

        // Manually use the iterator
        while (iterator.hasNext()) {
            Player player = iterator.next();
            result.append(" - ").append(player.getName()).append("\n");
        }

        showToast("Check logs for custom iterator demo results");
        System.out.println(result.toString());
    }

    /**
     * Show details for a player
     * @param player the player to show details for
     */
    private void showPlayerDetails(Player player) {
        // Simple toast to show player details
        showToast("Selected: " + player.getName());
    }

    /**
     * Helper method to show toast
     * @param message the message to show
     */
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
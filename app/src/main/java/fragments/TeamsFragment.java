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

import com.example.soccer.DataProvider;
import com.example.soccer.R;
import com.example.soccer.adapter.TeamAdapter;
import com.example.soccer.model.Match;
import com.example.soccer.model.Player;
import com.example.soccer.model.Team;
import com.example.soccer.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class TeamsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private ArrayList<Team> teams;
    TextView tvEmptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        initViews();

        //set up sample data using DataProvider
        //get test data from a DataProvider object
        DataProvider dataProvider = new DataProvider();
        List<Team> teams = dataProvider.createSampleTeams();
        List<Player> players = dataProvider.createSamplePlayers();
        List<Match> matches = dataProvider.createSampleMatches();

        setupRecyclerView();

        setupButtonListeners();

        return view;
    }

    /**
     * Initialize UI components
     */
    private void initViews() {
        tvEmptyView = tvEmptyView.findViewById(R.id.team_tv_empty_view);

    }

    /**
     * Set up the RecyclerView and adapter
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the adapter with an empty list initially
        adapter = new TeamAdapter(
                this,
                new ArrayList<>(),
                (team, position) -> {
                    // Lambda function for item click
                    showItemDetails(team);
                }
        );
        recyclerView.setAdapter(adapter);

        // Update adapter with all items
        updateAdapterItems(recyclerView.getAllItems());
    }

    /**
     * Set up click listeners for filter buttons
     * Demonstrates use of lambda functions and generics
     */
    private void setupButtonListeners() {
        // Show all items
        Button btnShowAll = findViewById(R.id.btn_show_all);
        btnShowAll.setOnClickListener(v -> {
            // Lambda function for click handler
            updateAdapterItems(inventoryContainer.getAllItems());
            showToast("Showing all items");
        });

        // Filter for books only using lambda predicate
        Button btnFilterBooks = findViewById(R.id.btn_filter_books);
        btnFilterBooks.setOnClickListener(v -> {

            // Using lambda predicate with generics
            Predicate<InventoryItem> bookFilter = item -> item instanceof Book;
            InventoryContainer<InventoryItem> filtered = inventoryContainer.filter(bookFilter);

            updateAdapterItems(filtered.getAllItems());
            showToast("Filtered: Books only");
        });

        // Filter for electronics only
        Button btnFilterElectronics = findViewById(R.id.btn_filter_electronics);
        btnFilterElectronics.setOnClickListener(v -> {
            // Lambda predicate example
            updateAdapterItems(
                    inventoryContainer.filter(item -> item instanceof Electronic).getAllItems()
            );
            showToast("Filtered: Electronics only");
        });

        // Filter for tools only
        Button btnFilterTools = findViewById(R.id.btn_filter_tools);
        btnFilterTools.setOnClickListener(v -> {
            updateAdapterItems(
                    inventoryContainer.filter(item -> item instanceof Tool).getAllItems()
            );
            showToast("Filtered: Tools only");
        });

        // Filter for expensive items (price > 100)
        Button btnFilterExpensive = findViewById(R.id.btn_filter_expensive);
        btnFilterExpensive.setOnClickListener(v -> {
            updateAdapterItems(
                    inventoryContainer.filter(item -> item.getPrice() > 100).getAllItems()
            );
            showToast("Filtered: Expensive items (>$100)");
        });

        // Sort by price (using lambda comparator)
        Button btnSortPrice = findViewById(R.id.btn_sort_price);
        btnSortPrice.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<InventoryItem> sortedItems = inventoryContainer.getAllItems().stream()
                    .sorted(Comparator.comparingDouble(InventoryItem::getPrice))
                    .collect(Collectors.toList());

            updateAdapterItems(sortedItems);
            showToast("Sorted by price (ascending)");
        });

        // Iterator demonstration - using for-each (which uses the Iterator interface)
        Button btnForeachIterator = findViewById(R.id.btn_foreach_iterator);
        btnForeachIterator.setOnClickListener(v -> {
            demonstrateForEachIterator();
        });

        // Iterator demonstration - using custom iterator
        Button btnCustomIterator = findViewById(R.id.btn_custom_iterator);
        btnCustomIterator.setOnClickListener(v -> {
            demonstrateCustomIterator();
        });
    }

    /**
     * Update the adapter items and manage empty view
     * @param teams the new items to display
     */
    private void updateAdapterItems(List<Team> teams) {
        adapter.updateTeams(teams);

        // Show/hide empty view
        if (teams.isEmpty()) {
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
        for (Team team : recyclerView) {
            result.append(" - ").append(team.getName()).append("\n");
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
        Repository<Team>.RepositoryIterator iterator =
                inventoryContainer.getCustomIterator();

        // Manually use the iterator
        while (iterator.hasNext()) {
            Team team = iterator.next();
            result.append(" - ").append(team.getName()).append("\n");
        }

        showToast("Check logs for custom iterator demo results");
        System.out.println(result.toString());
    }

    /**
     * Show details for an item
     * @param team the item to show details for
     */
    private void showItemDetails(Team team) {
        // Simple toast to show item details
        showToast("Selected: " + team.getName());
    }

    /**
     * Helper method to show toast
     * @param message the message to show
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
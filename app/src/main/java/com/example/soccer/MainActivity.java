package com.example.soccer;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    //variables for UI components:
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private PageAdapter adapter;
    private TextView tvEmptyView;

    //generic repository to contain objects like Player, Team and Match
    private RecyclerView playerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();

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

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


        //set up sample data using DataProvider
        DataProvider provider = new DataProvider();
        provider.createSampleMatches();

        setupRecyclerView();

        setupButtonListeners();
    }
    /**
     * Initialize UI components
     */
    private void initViews() {
        playerRepository = findViewById(R.id.player_recycler_view);
        tvEmptyView = findViewById(R.id.tv_empty_view);
    }

    /**
     * Set up the RecyclerView and adapter
     */
    private void setupRecyclerView() {
        playerRepository.setLayoutManager(new LinearLayoutManager(this));

        // Create the adapter with an empty list initially
        adapter = new PageAdapter(
                this,
                new ArrayList<>(),
                (item, position) -> {
                    // Lambda function for item click
                    showItemDetails(item);
                }
        );

        playerRepository.setAdapter(adapter);

        // Update adapter with all items
        updateAdapterItems(inventoryContainer.getAllItems());
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
     * @param matches the new items to display
     */
    private void updateAdapterItems(List<Match> matches) {
        adapter.updateMatches(matches);

        // Show/hide empty view
        if (matches.isEmpty()) {
            tvEmptyView.setVisibility(android.view.View.VISIBLE);
            playerRepository.setVisibility(android.view.View.GONE);
        } else {
            tvEmptyView.setVisibility(android.view.View.GONE);
            playerRepository.setVisibility(android.view.View.VISIBLE);
        }
    }

    /**
     * Demonstrate using the for-each loop which uses the Iterator interface
     * Shows how implementing Iterable allows for enhanced for loop
     */
    private void demonstrateForEachIterator() {
        StringBuilder result = new StringBuilder("Using for-each loop (Iterator):\n");

        // Using the for-each loop which uses the Iterator interface
        for (Player player : playerRepository) {
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
        Repository<SoccerEntity>.RepositoryIterator iterator =
                inventoryContainer.getCustomIterator();

        // Manually use the iterator
        while (iterator.hasNext()) {
            InventoryItem item = iterator.next();
            result.append(" - ").append(item.getName()).append("\n");
        }

        showToast("Check logs for custom iterator demo results");
        System.out.println(result.toString());
    }

    /**
     * Show details for an item
     * @param item the item to show details for
     */
    private void showItemDetails(InventoryItem item) {
        // Simple toast to show item details
        showToast("Selected: " + item.getName());
    }

    /**
     * Helper method to show toast
     * @param message the message to show
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


        //get test data from a DataProvider object
        DataProvider dataProvider = new DataProvider();
        List<Team> teams = dataProvider.createSampleTeams();
        List<Player> players = dataProvider.createSamplePlayers();
        List<Match> matches = dataProvider.createSampleMatches();
    }
}
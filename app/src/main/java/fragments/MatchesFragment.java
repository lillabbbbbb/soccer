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
import com.example.soccer.adapter.MatchAdapter;
import com.example.soccer.model.Match;
import com.example.soccer.repository.Repository;
import com.example.soccer.repository.MatchRepository;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MatchesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private MatchRepository<Match> matchRepository;
    TextView tvEmptyView;
    MaterialCardView filterView, iteratorView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        initViews(view);

        setupMatchRepository(setupData());

        setupRecyclerView();

        setupButtonListeners(view);

        return view;
    }

    /**
     * Initialize UI components
     */
    private void initViews(View view) {
        tvEmptyView = view.findViewById(R.id.match_tv_empty_view);
        filterView = view.findViewById(R.id.match_card_filter_options);
        iteratorView = view.findViewById(R.id.match_card_iterator_demo);
        recyclerView = view.findViewById(R.id.match_recycler_view);

    }

    /**
     * Set up the RecyclerView and adapter
     */
    private List<Match> setupData(){
        //set up sample data using DataProvider
        //get test data from a DataProvider object
        DataProvider dataProvider = new DataProvider();
        return new ArrayList<>(dataProvider.createSampleMatches());
    }
    private void setupMatchRepository(List<Match> matches){
        //initialize matchRepository
        matchRepository = new MatchRepository<>();

        //append matches to the matchRepository
        for(int i = 0; i < matches.size(); i++){
            matchRepository.addItem(matches.get(i));
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the adapter with an empty list initially
        adapter = new MatchAdapter(
                getContext(),
                new ArrayList<>(),
                (match, position) -> {
                    // Lambda function for match click
                    showMatchDetails(match);
                }
        );
        recyclerView.setAdapter(adapter);

        // Update adapter with all matches
        updateAdapterItems(matchRepository.getAllItems());
    }

    /**
     * Set up click listeners for filter buttons
     * Demonstrates use of lambda functions and generics
     */
    private void setupButtonListeners(View view) {
        // Instantiate all buttons of this fragment
        Button btnFilterBayernMunich, btnFilterRealMadrid, btnFilterChampionsLeague, btnSortChampionship, btnSortCity, btnSortHomeTeam, btnSortByDate, btnForeachIterator, btnCustomIterator;

        // Show all matches
        Button btnShowAll = view.findViewById(R.id.match_btn_show_all);
        btnShowAll.setOnClickListener(v -> {
            // Lambda function for click handler
            updateAdapterItems(matchRepository.getAllItems());
            showToast("Showing all matches");
        });

        // Filter for Bayern Munich  
        btnFilterBayernMunich = view.findViewById(R.id.match_btn_filter_bayern_munich);
        btnFilterBayernMunich.setOnClickListener(v -> {

            updateAdapterItems(matchRepository.filterByTeam("Bayern Munich").getAllItems());
            showToast("Filtered: Bayern Munich");
        });

        // Filter for Real Madrid
        btnFilterRealMadrid = view.findViewById(R.id.match_btn_filter_real_madrid);
        btnFilterRealMadrid.setOnClickListener(v -> {
            // Lambda predicate example
            updateAdapterItems(
                    matchRepository.filterByTeam("Real Madrid").getAllItems()
            );
            showToast("Filtered: Real Madrid");
        });

        // Filter for tools  
        btnFilterChampionsLeague = view.findViewById(R.id.match_btn_filter_champions_league);
        btnFilterChampionsLeague.setOnClickListener(v -> {
            updateAdapterItems(
                    matchRepository.filterByChampionship("Champions League").getAllItems()
            );
            showToast("Filtered: Champions League");
        });

        // Sort by name (A-Z) (using lambda comparator)
        btnSortChampionship = view.findViewById(R.id.match_btn_sort_championship);
        btnSortChampionship.setOnClickListener(v -> {
            List<Match> sortedMatchs = (List<Match>) matchRepository.sortByChampionship();
            showToast("Sorted by Name (>A-Z)");
        });

        // Sort by age (Ascending) (using lambda comparator)
        btnSortCity = view.findViewById(R.id.match_btn_sort_city);
        btnSortCity.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<Match> sortedMatchs = (List<Match>) matchRepository.sortByCity();

            updateAdapterItems(sortedMatchs);
            showToast("Sorted by Age (Ascending)");
        });

        // Sort by date (using lambda comparator)
        btnSortByDate = view.findViewById(R.id.match_btn_sort_date);
        btnSortByDate.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<Match> sortedMatches = (List<Match>) matchRepository.sortByDate();

            updateAdapterItems(sortedMatches);
            showToast("Sorted by Date");
        });

        // Sort by team (A-Z) (using lambda comparator)
        btnSortHomeTeam = view.findViewById(R.id.match_btn_sort_home_team);
        btnSortHomeTeam.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<Match> sortedMatches = (List<Match>) matchRepository.sortByHomeTeam();

            updateAdapterItems(sortedMatches);
            showToast("Sorted by Home Team (A-Z)");
        });



        // Iterator demonstration - using for-each (which uses the Iterator interface)
        btnForeachIterator = view.findViewById(R.id.match_btn_foreach_iterator);
        btnForeachIterator.setOnClickListener(v -> {
            demonstrateForEachIterator();
        });

        // Iterator demonstration - using custom iterator
        btnCustomIterator = view.findViewById(R.id.match_btn_custom_iterator);
        btnCustomIterator.setOnClickListener(v -> {
            demonstrateCustomIterator();
        });
    }

    /**
     * Update the adapter matches and manage empty view
     * @param matches the new matches to display
     */
    private void updateAdapterItems(List<Match> matches) {
        adapter.updateItems(matches);

        // Show/hide empty view
        if (matches.isEmpty()) {
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
        for (Object object : matchRepository.getAllItems()) {
            Match match = (Match) object;
            result.append(" - ").append(match.getName()).append("\n");
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
        Repository<Match>.RepositoryIterator iterator =
                matchRepository.getCustomIterator();

        // Manually use the iterator
        while (iterator.hasNext()) {
            Match match = iterator.next();
            result.append(" - ").append(match.getName()).append("\n");
        }

        showToast("Check logs for custom iterator demo results");
        System.out.println(result.toString());
    }

    /**
     * Show details for a match
     * @param match the match to show details for
     */
    private void showMatchDetails(Match match) {
        // Simple toast to show match details
        showToast("Selected: " + match.getName());
    }

    /**
     * Helper method to show toast
     * @param message the message to show
     */
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
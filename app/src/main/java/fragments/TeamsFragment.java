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
import com.example.soccer.adapter.TeamAdapter;
import com.example.soccer.model.Team;
import com.example.soccer.repository.Repository;
import com.example.soccer.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TeamsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private TeamRepository<Team> teamRepository;
    TextView tvEmptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        initViews();

        setupTeamRepository(setupData());

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
    private List<Team> setupData(){
        //set up sample data using DataProvider
        //get test data from a DataProvider object
        DataProvider dataProvider = new DataProvider();
        return new ArrayList<>(dataProvider.createSampleTeams());
    }
    private void setupTeamRepository(List<Team> teams){
        //initialize teamRepository
        teamRepository = new TeamRepository<>();

        //append teams to the teamRepository
        for(int i = 0; i < teams.size(); i++){
            teamRepository.addItem(teams.get(i));
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the adapter with an empty list initially
        adapter = new TeamAdapter(
                getContext(),
                new ArrayList<>(),
                (team, position) -> {
                    // Lambda function for item click
                    showItemDetails(team);
                }
        );
        recyclerView.setAdapter(adapter);

        // Update adapter with all items
        updateAdapterItems(teamRepository.getAllItems());
    }

    /**
     * Set up click listeners for filter buttons
     * Demonstrates use of lambda functions and generics
     */
    private void setupButtonListeners() {
        // Instantiate all buttons of this fragment
        Button btnFilterLiverpool, btnFilterFcBarcelona, btnFilterEngland, btnSortName, btnSortFounding;

        // Show all teams
        Button btnShowAll = getActivity().findViewById(R.id.team_btn_show_all);
        btnShowAll.setOnClickListener(v -> {
            // Lambda function for click handler
            updateAdapterItems(teamRepository.getAllItems());
            showToast("Showing all teams");
        });

        // Filter teams from England
        btnFilterLiverpool = getActivity().findViewById(R.id.team_btn_filter_liverpool);
        btnFilterLiverpool.setOnClickListener(v -> {

            // Using lambda predicate with generics
            Predicate<Team> liverpoolFilter = team -> team.getName() == "Liverpool";
            Repository<Team> filtered = teamRepository.filter(liverpoolFilter);

            updateAdapterItems(filtered.getAllItems());
            showToast("Filtered: Liverpool only");
        });

        // Filter for FC Barcelona team only
        btnFilterFcBarcelona = getActivity().findViewById(R.id.team_btn_filter_fc_barcelona);
        btnFilterFcBarcelona.setOnClickListener(v -> {
            // Lambda predicate example
            updateAdapterItems(
                    teamRepository.filter(team -> team.getName() == "FC Barcelona").getAllItems()
            );
            showToast("Filtered: FC Barcelona only");
        });

        // Filter for tools only
        btnFilterEngland = getActivity().findViewById(R.id.team_btn_filter_england);
        btnFilterEngland.setOnClickListener(v -> {
            updateAdapterItems(
                    teamRepository.filter(team -> team.getCountry() == "England").getAllItems()
            );
            showToast("Filtered: Tools only");
        });

        // Sort by name (A-Z) (using lambda comparator)
        btnSortName = getActivity().findViewById(R.id.team_btn_sort_name);
        btnSortName.setOnClickListener(v -> {
            List<Team> sortedTeams = (List<Team>) teamRepository.getAllItems().stream()
                    .sorted(Comparator.comparing(Team::getName))
                    .collect(Collectors.toList());
            showToast("Filtered: Expensive items (>$100)");
        });

        // Sort by year of founding (using lambda comparator)
        btnSortFounding = getActivity().findViewById(R.id.team_btn_sort_founding);
        btnSortFounding.setOnClickListener(v -> {
            // Using stream with lambda comparator
            List<Team> sortedTeams = (List<Team>) teamRepository.getAllItems().stream()
                    .sorted(Comparator.comparingDouble(Team::getYear))
                    .collect(Collectors.toList());

            updateAdapterItems(sortedTeams);
            showToast("Sorted by price (ascending)");
        });


        // Iterator demonstration - using for-each (which uses the Iterator interface)
        Button btnForeachIterator = getView().findViewById(R.id.team_btn_foreach_iterator);
        btnForeachIterator.setOnClickListener(v -> {
            demonstrateForEachIterator();
        });

        // Iterator demonstration - using custom iterator
        Button btnCustomIterator = getView().findViewById(R.id.team_btn_custom_iterator);
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
        for (Team team : teamRepository) {
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
                teamRepository.getCustomIterator();

        // Manually use the iterator
        while (iterator.hasNext()) {
            Team team = iterator.next();
            result.append(" - ").append(team.getName()).append("\n");
        }

        showToast("Check logs for custom iterator demo results");
        System.out.println(result.toString());
    }

    /**
     * Show details for a team
     * @param team the team to show details for
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
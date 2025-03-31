package com.example.soccer.repository;

import com.example.soccer.model.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TeamRepository<Team> extends Repository{
    public Repository<Team> filterByLeague(String league){
        Predicate<com.example.soccer.model.Team> leagueFilter = team -> team.getLeague().equals(league);
        return super.filter(leagueFilter);

    }

    public Repository<Team> filterByName(String name){
        Predicate<com.example.soccer.model.Team> nameFilter = team -> team.getName().equals(name);
        return super.filter(nameFilter);
    }

    public Repository<Team> filterByCountry(String country){
        Predicate<com.example.soccer.model.Team> countryFilter = team -> team.getCountry().equals(country);
        return super.filter(countryFilter);
    }

    public List<Team> sortByName(){
        return (List<Team>)this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Team))  // Ensure items are of type Team
                .map(item -> (Team) item)  // Cast each item to Team
                .sorted(Comparator.comparing(com.example.soccer.model.Team::getName))
                .collect(Collectors.toList());
    }

    public List<Team> sortByYear(){
        return (List<Team>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Team))  // Ensure items are of type Team
                .map(item -> (Team) item)  // Cast each item to Team
                .sorted(Comparator.comparingInt(com.example.soccer.model.Team::getYear))
                .collect(Collectors.toList());
    }
}

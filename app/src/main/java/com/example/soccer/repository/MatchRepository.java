package com.example.soccer.repository;

import com.example.soccer.model.Match;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MatchRepository<Match> extends Repository{

    public Repository<Match> filterByTeam(String team){
        Predicate<com.example.soccer.model.Match> teamFilter = match -> match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team);
        return super.filter(teamFilter);

    }

    public Repository<Match> filterByChampionship(String championship){
        Predicate<com.example.soccer.model.Match> championshipFilter = match -> match.getChampionship().equals(championship);
        return super.filter(championshipFilter);

    }

    public Repository<Match> filterByCity(String city){
        Predicate<com.example.soccer.model.Match> cityFilter = match -> match.getLocation().equals(city);
        return super.filter(cityFilter);
    }

    public List<Match> sortByCity(){
        return (List<Match>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Match))  // Ensure items are of type Match
                .map(item -> (Match) item)  // Cast each item to Match
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getLocation))
                .collect(Collectors.toList());
    }

    public List<Match> sortByHomeTeam(){
        return (List<Match>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Match))  // Ensure items are of type Match
                .map(item -> (Match) item)  // Cast each item to Match
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getHomeTeam))
                .collect(Collectors.toList());
    }
    public List<Match> sortByChampionship(){
        return (List<Match>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Match))  // Ensure items are of type Match
                .map(item -> (Match) item)  // Cast each item to Match
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getChampionship))
                .collect(Collectors.toList());
    }

    public List<Match> sortByDate(){
        return (List<Match>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Match))  // Ensure items are of type Match
                .map(item -> (Match) item)  // Cast each item to Match
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getDate))
                .collect(Collectors.toList());
    }
}

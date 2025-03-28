package com.example.soccer.repository;

import com.example.soccer.model.Match;

import java.util.Comparator;
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

    public Repository<Match> sortByCity(){
        return (Repository<Match>) this.getAllItems().stream()
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getLocation))
                .collect(Collectors.toList());
    }

    public Repository<Match> sortByHomeTeam(){
        return (Repository<Match>) this.getAllItems().stream()
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getHomeTeam))
                .collect(Collectors.toList());
    }
    public Repository<Match> sortByChampionship(){
        return (Repository<Match>) this.getAllItems().stream()
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getChampionship))
                .collect(Collectors.toList());
    }

    public Repository<Match> sortByDate(){
        return (Repository<Match>) this.getAllItems().stream()
                .sorted(Comparator.comparing(com.example.soccer.model.Match::getDate))
                .collect(Collectors.toList());
    }
}

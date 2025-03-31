package com.example.soccer.repository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerRepository<Player> extends Repository{

    public Repository<Player> filterByPosition(String position){
        Predicate<com.example.soccer.model.Player> leagueFilter = player -> player.getPosition().equals(position);
        return super.filter(leagueFilter);

    }

    public Repository<Player> filterByTeam(String team){
        Predicate<com.example.soccer.model.Player> teamFilter = player -> player.getTeam().equals(team);
        return super.filter(teamFilter);

    }

    public Repository<Player> filterByName(String name){
        Predicate<com.example.soccer.model.Player> nameFilter = player -> player.getName().equals(name);
        return super.filter(nameFilter);
    }

    public Repository<Player> filterByCountry(String country){
        Predicate<com.example.soccer.model.Player> countryFilter = player -> player.getName().equals(country);
        return super.filter(countryFilter);
    }
    public List<Player> sortByName(){
        return (List<Player>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Player))  // Ensure items are of type Player
                .map(item -> (Player) item)  // Cast each item to Player
                .sorted(Comparator.comparing(com.example.soccer.model.Player::getName))
                .collect(Collectors.toList());
    }

    public List<Player> sortByTeam(){
        return (List<Player>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Player))  // Ensure items are of type Player
                .map(item -> (Player) item)  // Cast each item to Player
                .sorted(Comparator.comparing(com.example.soccer.model.Player::getTeam))
                .collect(Collectors.toList());
    }

    public List<Player> sortByAge(){
        return (List<Player>) this.getAllItems().stream()
                .filter(item -> (item instanceof com.example.soccer.model.Player))  // Ensure items are of type Player
                .map(item -> (Player) item)  // Cast each item to Player
                .sorted(Comparator.comparingInt(com.example.soccer.model.Player::getAge))
                .collect(Collectors.toList());
    }
}

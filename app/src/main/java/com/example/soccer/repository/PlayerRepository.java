package com.example.soccer.repository;

import java.util.Comparator;
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
    public Repository<Player> sortByName(){
        return (Repository<Player>) this.getAllItems().stream()
                .sorted(Comparator.comparing(com.example.soccer.model.Player::getName))
                .collect(Collectors.toList());
    }

    public Repository<Player> sortByTeam(){
        return (Repository<Player>) this.getAllItems().stream()
                .sorted(Comparator.comparing(com.example.soccer.model.Player::getTeam))
                .collect(Collectors.toList());
    }

    public Repository<Player> sortByAge(){
        return (Repository<Player>) this.getAllItems().stream()
                .sorted(Comparator.comparingInt(com.example.soccer.model.Player::getAge))
                .collect(Collectors.toList());
    }
}

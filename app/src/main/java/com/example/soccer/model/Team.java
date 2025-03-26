package com.example.soccer.model;

public class Team implements SoccerEntity{
   private String name;
   private String country;
   private String league;
   private String home;
   private int year;

    public Team(String name, String country, String league, String home, int year) {
        this.name = name;
        this.country = country;
        this.league = league;
        this.home = home;
        this.year = year;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getName() {
        return name;
    }
}

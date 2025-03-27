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

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

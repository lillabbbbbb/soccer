package com.example.soccer.model;

public class Player implements SoccerEntity{
    private String name;
    int age;
    private String country;
    private String position;
    private String team;
    private int points;

    public Player(String name, int age, String country, String position, String team, int points) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.position = position;
        this.team = team;
        this.points = points;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }
}

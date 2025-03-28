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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

package com.example.soccer.model;

public class Match implements SoccerEntity{
    private String homeTeam;
    private String awayTeam;
    private String score;
    private String homeTeamHome;
    private String date;
    private String awayTeamHome;

    public Match(String homeTeam, String awayTeam, String score, String homeTeamHome, String awayTeamHome, String date) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
        this.homeTeamHome = homeTeamHome;
        this.date = date;
        this.awayTeamHome = awayTeamHome;
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

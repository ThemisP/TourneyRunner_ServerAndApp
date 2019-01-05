package com.tourneyrunner.tourneyrunner.fixtures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourneyrunner.tourneyrunner.tournaments.Tournament;

import javax.persistence.*;

@Entity
public class Fixture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Fixture_id")
    private Long id;
    private int stage;
    private String player1;
    private String player2;
    private String winner;
    private int scorePlayer1;
    private int scorePlayer2;

    @ManyToOne
    @JsonIgnore
    private Tournament tournament;

    public Fixture(){}

    public Fixture(int stage, String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.stage = stage;
        this.scorePlayer1 = 0;
        this.scorePlayer2 = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        if(!tournament.getFixtures().contains(this)) tournament.getFixtures().add(this);
    }
}

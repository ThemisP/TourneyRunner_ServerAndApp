package com.tourneyrunner.tourneyrunner.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tourneyrunner.tourneyrunner.fixtures.Fixture;
import com.tourneyrunner.tourneyrunner.teams.Team;
import com.tourneyrunner.tourneyrunner.tournaments.Tournament;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private Long id;

    @Column(name = "User_username",unique = true)
    private String username;
    private String name;
    private String surname;
    private String email;
    @JsonIgnore
    private String password;

    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    private List<Tournament> tournaments = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Team> teams = new ArrayList<>();

    public User() {
    }

    public User(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team){
        this.teams.add(team);
        if(!team.getUsers().contains(this)) team.addUser(this);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void addTournament(Tournament tournament){
        if(!this.tournaments.contains(tournament)){
            this.tournaments.add(tournament);
            if(!tournament.getFixtures().contains(this)) tournament.addParticipant(this);
        }
    }
}

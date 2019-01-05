package com.tourneyrunner.tourneyrunner.tournaments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tourneyrunner.tourneyrunner.fixtures.Fixture;
import com.tourneyrunner.tourneyrunner.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;
    private Boolean pending = true;
    private String admin;

    @OneToMany(mappedBy = "tournament")
    private List<Fixture> fixtures = new ArrayList<>();

    @ManyToMany
    private List<User> participants = new ArrayList<>();

    public Tournament(){}

    public Tournament(String name, String admin) {
        this.name = name;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public void addFixture(Fixture fixture){
        if(!this.fixtures.contains(fixture)){
            this.fixtures.add(fixture);
            if(fixture.getTournament() != this)
                fixture.setTournament(this);
        }
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User user){
        if(!this.participants.contains(user)){
            this.participants.add(user);
            if(!user.getTournaments().contains(this)) user.addTournament(this);
        }
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}

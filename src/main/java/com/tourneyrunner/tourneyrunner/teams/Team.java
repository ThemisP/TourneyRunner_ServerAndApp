package com.tourneyrunner.tourneyrunner.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourneyrunner.tourneyrunner.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Team_id")
    private long id;

    @Column(name = "Team_name",unique = true)
    private String name;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        if(!this.users.contains(user)) {
            this.users.add(user);
            if (!user.getTeams().contains(this)) user.addTeam(this);
        }
    }
}

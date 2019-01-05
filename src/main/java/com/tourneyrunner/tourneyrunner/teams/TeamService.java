package com.tourneyrunner.tourneyrunner.teams;

import com.tourneyrunner.tourneyrunner.users.User;
import com.tourneyrunner.tourneyrunner.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRep;
    @Autowired
    private UserRepository userRep;

    public List<Team> getAllTeams() {
        List<Team> teamList = new ArrayList<>();
        teamRep.findAll().forEach(teamList::add);
        return teamList;
    }

    public void addTeam(Team team){
        teamRep.save(team);
    }

    public Team addUserToTeam(Long id, User user){
        Team team = teamRep.findOne(id);
        team.getUsers().add(userRep.findOne(user.getId()));
        teamRep.save(team);
        return team;
    }

    public Team getTeamByName(String name){
        return teamRep.findByName(name);
    }
}

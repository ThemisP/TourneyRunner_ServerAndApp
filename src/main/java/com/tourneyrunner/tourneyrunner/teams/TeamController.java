package com.tourneyrunner.tourneyrunner.teams;

import com.tourneyrunner.tourneyrunner.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @RequestMapping(method =  RequestMethod.GET)
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addTeam(@RequestBody Team team){
        teamService.addTeam(team);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Team getTeamByName(@PathVariable String name){
        return teamService.getTeamByName(name);
    }

    @RequestMapping(value = "/{id}/addUser",method = RequestMethod.POST)
    public Team addUserToTeam(@PathVariable Long id,@RequestBody User user){
        return teamService.addUserToTeam(id, user);
    }
}

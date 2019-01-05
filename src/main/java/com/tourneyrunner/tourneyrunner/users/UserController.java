package com.tourneyrunner.tourneyrunner.users;

import com.tourneyrunner.tourneyrunner.teams.Team;
import com.tourneyrunner.tourneyrunner.tournaments.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @RequestMapping(value = "/{username}/teams", method = RequestMethod.GET)
    public List<Team> getUserTeams(@PathVariable String username){
        return userService.getUserTeams(username);
    }

    @RequestMapping(value = "/{username}/tournaments", method = RequestMethod.GET)
    public List<Tournament> getUserTournaments(@PathVariable String username){
        return userService.getUserTournaments(username);
    }

    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    public User authenticateUser(@PathVariable String username,@PathVariable String password){
        return userService.authenticateUser(username, password);
    }
}

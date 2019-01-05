package com.tourneyrunner.tourneyrunner.users;

import com.tourneyrunner.tourneyrunner.teams.Team;
import com.tourneyrunner.tourneyrunner.tournaments.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRep;

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        userRep.findAll().forEach(userList::add);
        return userList;
    }

    public User addUser(User user){
        userRep.save(user);
        return userRep.findOne(user.getId());
    }

    public User getUserByUsername(String username){
        return userRep.findByUsername(username);
    }

    public List<Team> getUserTeams(String username){
        return userRep.findByUsername(username).getTeams();
    }

    public List<Tournament> getUserTournaments(String username){
        return userRep.findByUsername(username).getTournaments();
    }

    public User authenticateUser(String username, String password) {
        User user = userRep.findByUsername(username);
        if(user!=null){
            if(user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}

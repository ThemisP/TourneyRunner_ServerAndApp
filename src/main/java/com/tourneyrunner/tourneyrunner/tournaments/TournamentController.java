package com.tourneyrunner.tourneyrunner.tournaments;

import com.tourneyrunner.tourneyrunner.fixtures.Fixture;
import com.tourneyrunner.tourneyrunner.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Tournament> getAllTournaments(){
        return tournamentService.getAllTournaments();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Tournament getTournamentById(@PathVariable Long id){
        return tournamentService.getTournamentById(id);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Tournament getTournamentByName(@PathVariable String name){
        return tournamentService.getTournamentByName(name);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Tournament createTournament(@RequestBody Tournament tournament){
        return tournamentService.createTournament(tournament);
    }

    @RequestMapping(value = "/delete/{tournament_id}", method = RequestMethod.GET)
    public void deleteTournamentIfPending(@PathVariable Long tournament_id){
        tournamentService.deleteTournamentIfPending(tournament_id);
    }

    @RequestMapping(value = "/{tournament_id}/finalize", method = RequestMethod.PUT)
    public Tournament addUsersAndFinalize(@PathVariable Long tournament_id,@RequestBody List<User> users){
           return tournamentService.addUsersAndFinalize(tournament_id, users);
    }

    @RequestMapping(value = "/{tournament_id}/fixtures", method = RequestMethod.GET)
    public List<Fixture> getTournamentFixtures(@PathVariable Long tournament_id){
        return tournamentService.getTournamentFixtures(tournament_id);
    }

}

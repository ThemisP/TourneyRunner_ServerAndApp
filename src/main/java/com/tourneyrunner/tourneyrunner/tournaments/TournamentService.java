package com.tourneyrunner.tourneyrunner.tournaments;

import com.tourneyrunner.tourneyrunner.fixtures.Fixture;
import com.tourneyrunner.tourneyrunner.fixtures.FixtureRepository;
import com.tourneyrunner.tourneyrunner.users.User;
import com.tourneyrunner.tourneyrunner.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TournamentService {
    @Autowired
    private TournamentRepository tournamentRep;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FixtureRepository fixtureRepository;

    public List<Tournament> getAllTournaments(){
        List<Tournament> tournaments = new ArrayList<>();
        tournamentRep.findAll().forEach(tournaments::add);
        return tournaments;
    }

    public Tournament getTournamentById(Long id){
        return tournamentRep.findOne(id);
    }

    public Tournament createTournament(Tournament tournament) {
        tournamentRep.save(tournament);
        return tournamentRep.findOne(tournament.getId());
    }

    public Tournament addUsersAndFinalize(Long tournament_id, List<User> users){
        Tournament tournament = tournamentRep.findOne(tournament_id);
        if(tournament.getPending()){
            if(!users.isEmpty()){
                for(User user : users){
                    User userMatch = userRepository.findOne(user.getId());
                    tournament.addParticipant(userMatch);
                }
            }
            tournament = finalizeTournament(tournament);
        }
        return tournament;
    }

    public Tournament getTournamentByName(String name) {
        return tournamentRep.findByName(name);
    }

    public List<Fixture> getTournamentFixtures(Long tournament_id) {
        return fixtureRepository.findAllByTournament(tournamentRep.findOne(tournament_id));
    }

    private Tournament finalizeTournament(Tournament tournament){
        List<User> participants = new ArrayList<>(tournament.getParticipants());
        int size = (int) Math.ceil(((double)participants.size())/2.0);
        for(int i=0; i<size; i++){
            int randomPick = new Random().nextInt(participants.size());
            User player1 = participants.get(randomPick);
            participants.remove(randomPick);
            if(!participants.isEmpty()){
                randomPick = new Random().nextInt(participants.size());
                User player2 = participants.get(randomPick);
                participants.remove(randomPick);
                Fixture fixture = new Fixture(1,player1.getUsername(),player2.getUsername());
                fixtureRepository.save(fixture);
                tournament.addFixture(fixture);
            } else {
                Fixture fixture = new Fixture(1, player1.getUsername(), null);
                fixture.setWinner(player1.getUsername());
                fixtureRepository.save(fixture);
                tournament.addFixture(fixture);
            }
        }
        tournament = createTournamentFixtures(tournament, size);
        tournament.setPending(false);
        tournamentRep.save(tournament);
        return tournamentRep.findOne(tournament.getId());
    }

    private Tournament createTournamentFixtures(Tournament tournament, int size){
        int stageCount = 2;
        while(size>1){
            size = (int)Math.ceil((float)size/2.0);
            for(int i=0; i<size;i++) {
                Fixture fixture = new Fixture(stageCount, null, null);
                fixtureRepository.save(fixture);
                tournament.addFixture(fixture);
            }
            stageCount++;
        }
        return tournament;
    }

    public void deleteTournamentIfPending(Long tournament_id) {
        Tournament tournament = tournamentRep.findOne(tournament_id);
        if(tournament.getPending()){
            tournamentRep.delete(tournament_id);
        }
    }
}

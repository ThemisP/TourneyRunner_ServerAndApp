package com.tourneyrunner.tourneyrunner;

import com.tourneyrunner.tourneyrunner.fixtures.Fixture;
import com.tourneyrunner.tourneyrunner.fixtures.FixtureRepository;
import com.tourneyrunner.tourneyrunner.teams.Team;
import com.tourneyrunner.tourneyrunner.teams.TeamRepository;
import com.tourneyrunner.tourneyrunner.tournaments.Tournament;
import com.tourneyrunner.tourneyrunner.tournaments.TournamentRepository;
import com.tourneyrunner.tourneyrunner.tournaments.TournamentService;
import com.tourneyrunner.tourneyrunner.users.User;
import com.tourneyrunner.tourneyrunner.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class exampleData implements ApplicationRunner {

    private final TeamRepository teams;
    private final UserRepository users;
    private final TournamentRepository tournaments;
    private final FixtureRepository fixtures;
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    public exampleData(TeamRepository teams, UserRepository users, TournamentRepository tournaments, FixtureRepository fixtures) {
        this.teams = teams;
        this.users = users;
        this.tournaments = tournaments;
        this.fixtures = fixtures;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Team> teamList = new ArrayList<>(Arrays.asList(
                new Team("Netiquette"),
                new Team("newbies"),
                new Team("RunnerWinners")
        ));


        List<User> userList = new ArrayList<>(Arrays.asList(
                new User("Chopper","Themis","Papathemistocleous","tp16381@my.bristol.ac.uk", "12345"),
                new User("poolGuy","George","lolsda","poolGuy@gmail.com", "12345"),
                new User("dupUser","noLsid","llittle","dup@gmail.com","12345"),
                new User("user1","Ash","sdads","ash@gmail.com","12345"),
                new User("user2","Arun","lodssa","arun@gmail.com","12345"),
                new User("user3","Olie","lsdle","olie@gmail.com","12345"),
                new User("user4","Newssda","sadtds","newssda@gmail.com","12345"),
                new User("user5","sam","dvvldfsa","sam@gmail.com","12345"),
                new User("user6","sadaw","csam","sadaw@gmail.com","12345"),
                new User("user7","sdawwd","dsado","sdawwd@gmail.com","12345")
        ));

        List<Tournament> tournamentsList = new ArrayList<>(Arrays.asList(
                new Tournament("Pool Tournament", "Chopper"),
                new Tournament("Mario Bros", "poolGuy"),
                new Tournament("Football Tournament", "user1")
        ));

        List<Fixture> fixturesList = new ArrayList<>(Arrays.asList(
                new Fixture(1,"Chopper","poolGuy"),
                new Fixture(1,"user1","dupUser"),
                new Fixture(2, null, null)
        ));

        tournamentsList.get(0).addFixture(fixturesList.get(0));
        tournamentsList.get(0).addFixture(fixturesList.get(1));
        tournamentsList.get(0).addFixture(fixturesList.get(2));
        tournamentsList.get(0).addParticipant(userList.get(0));
        tournamentsList.get(0).addParticipant(userList.get(1));
        tournamentsList.get(0).addParticipant(userList.get(2));
        tournamentsList.get(0).addParticipant(userList.get(3));
        tournamentsList.get(0).setPending(false);

        userList.get(0).addTeam(teamList.get(0));

        users.save(userList);
        teams.save(teamList);
        tournaments.save(tournamentsList);
        fixtures.save(fixturesList);

    }
}

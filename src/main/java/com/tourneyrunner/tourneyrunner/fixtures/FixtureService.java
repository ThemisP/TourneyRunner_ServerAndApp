package com.tourneyrunner.tourneyrunner.fixtures;

import com.tourneyrunner.tourneyrunner.tournaments.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FixtureService {
    @Autowired
    private FixtureRepository fixtureRepository;

    public List<Fixture> getAllFixtures() {
        List<Fixture> fixtures = new ArrayList<>();
        fixtureRepository.findAll().forEach(fixtures::add);
        return fixtures;
    }

    public Fixture addScoreToFixture(Fixture fixture) {
        Fixture updateFixture = fixtureRepository.findOne(fixture.getId());
        updateFixture.setWinner(fixture.getWinner());
        updateFixture.setScorePlayer1(fixture.getScorePlayer1());
        updateFixture.setScorePlayer2(fixture.getScorePlayer2());
        fixtureRepository.save(updateFixture);

        List<Fixture> nextStage = fixtureRepository.findAllByStage(fixture.getStage()+1);
        boolean stop = false;
        if(!nextStage.isEmpty()){
            for (Fixture nextStageFixture : nextStage) {
                if(nextStageFixture.getPlayer1()==null && !stop){
                    nextStageFixture.setPlayer1(fixture.getWinner());
                    fixtureRepository.save(nextStageFixture);
                    stop = true;
                }
                if(nextStageFixture.getPlayer2()==null && !stop){
                    nextStageFixture.setPlayer2(fixture.getWinner());
                    fixtureRepository.save(nextStageFixture);
                    stop = true;
                }
            }
        }
        return updateFixture;
    }
}

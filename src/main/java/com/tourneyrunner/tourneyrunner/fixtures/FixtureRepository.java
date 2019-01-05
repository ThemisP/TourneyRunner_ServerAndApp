package com.tourneyrunner.tourneyrunner.fixtures;

import com.tourneyrunner.tourneyrunner.tournaments.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixtureRepository extends CrudRepository<Fixture, Long>{
    List<Fixture> findAllByTournament(Tournament tournament);
    List<Fixture> findAllByStage(int stage);
}

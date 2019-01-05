package com.tourneyrunner.tourneyrunner.tournaments;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends CrudRepository<Tournament, Long> {
    Tournament findByName(String name);
}

package com.tourneyrunner.tourneyrunner.teams;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long>{
    Team findByName(String name);
}

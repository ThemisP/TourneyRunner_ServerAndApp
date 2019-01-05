package com.tourneyrunner.tourneyrunner.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fixtures")
public class FixtureController {
    @Autowired
    private FixtureService fixtureService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Fixture> getAllFixtures(){
        return fixtureService.getAllFixtures();
    }

    @RequestMapping(value = "/score", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Fixture addScoreToFixture(@RequestBody Fixture fixture){
        return fixtureService.addScoreToFixture(fixture);
    }
}

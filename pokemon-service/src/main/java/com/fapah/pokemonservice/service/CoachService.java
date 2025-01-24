package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Coach;

import java.util.List;

public interface CoachService {

    List<Coach> getAllCoaches();

    CoachDto getCoachById(long coachId);

    CoachDto getCoachByName(String coachName);

    String addCoach(Coach coach);

    String deleteCoach(long coachId);

}

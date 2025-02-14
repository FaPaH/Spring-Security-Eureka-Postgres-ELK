package com.fapah.pokemonservice.service;


import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PokemonService {

    List<PokemonDto> getAllPokemons();

    PokemonDto getPokemonById(long pokemonId);

    PokemonDto getPokemonByName(String pokemonName);

    PokemonDto addPokemon(PokemonDto pokemonDto);

    String deletePokemon(long pokemonId);

    boolean checkPokemonHaveCoach(String pokemonName);
}

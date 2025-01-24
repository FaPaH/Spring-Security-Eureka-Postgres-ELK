package com.fapah.pokemonservice.service;


import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Pokemon;

import java.util.List;

public interface PokemonService {

    List<Pokemon> getAllPokemons();

    PokemonDto getPokemonById(long pokemonId);

    PokemonDto getPokemonByName(String pokemonName);

    String addPokemon(Pokemon pokemon);

    String deletePokemon(long pokemonId);
}

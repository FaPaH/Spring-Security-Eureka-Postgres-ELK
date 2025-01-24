package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Pokemon findByPokemomName(String name);
}

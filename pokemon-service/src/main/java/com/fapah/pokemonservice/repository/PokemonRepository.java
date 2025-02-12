package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByPokemonName(String name);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM pokemon p WHERE p.pokemon_name = :pokemonName AND p.pokemon_coach_id IS NOT NULL"
    )
    Optional<Pokemon> checkPokemonCoach(@Param("pokemonName") String name);
}

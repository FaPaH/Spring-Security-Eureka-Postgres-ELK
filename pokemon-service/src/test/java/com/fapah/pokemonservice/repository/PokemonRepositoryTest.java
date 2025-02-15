package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    private Pokemon pokemon;

    @BeforeEach

    void setup(){
        Type pokemonType = Type.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();

        pokemon = Pokemon.builder()
                .pokemonName("Pikachu")
                .pokemonType(new ArrayList<>(Arrays.asList(pokemonType)))
                .pokemonCoach(null)
                .pokemonHp(100)
                .pokemonAttack(100)
                .pokemonDefence(100)
                .pokemonHeight(100)
                .pokemonWeight(100)
                .build();
    }

    @Test
    public void testAddPokemon_whenValidPokemon_shouldReturnExpectedPokemon() {

        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
        Assertions.assertThat(savedPokemon.getPokemonName()).isEqualTo(pokemon.getPokemonName());
    }

    @Test
    public void testGetAllPokemons_whenDatabaseHavePokemons_shouldReturnAllPokemons() {

        Pokemon pokemon2 = Pokemon.builder()
                .pokemonName("Aboba")
                .pokemonType(pokemon.getPokemonType())
                .pokemonCoach(null)
                .pokemonHp(100)
                .pokemonAttack(100)
                .pokemonDefence(100)
                .pokemonHeight(100)
                .pokemonWeight(100)
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemons = pokemonRepository.findAll();

        Assertions.assertThat(pokemons).isNotNull();
        Assertions.assertThat(pokemons).hasSize(2);
    }

    @Test
    public void testGetPokemonById_whenValidPokemonId_shouldReturnExpectedPokemon() {

        pokemonRepository.save(pokemon);

        Pokemon pokemonTest = pokemonRepository.findById(pokemon.getId()).get();

        Assertions.assertThat(pokemonTest).isNotNull();
        Assertions.assertThat(pokemonTest.getPokemonName()).isEqualTo(pokemon.getPokemonName());
    }

    @Test
    public void testGetPokemonByName_whenValidPokemonName_shouldReturnExpectedPokemon() {

        pokemonRepository.save(pokemon);

        Pokemon pokemonTest = pokemonRepository.findByPokemonName(pokemon.getPokemonName()).get();

        Assertions.assertThat(pokemonTest).isNotNull();
        Assertions.assertThat(pokemonTest.getPokemonName()).isEqualTo(pokemon.getPokemonName());
    }

    @Test
    public void testUpdatePokemon_whenValidPokemon_shouldReturnExpectedPokemon() {

        pokemonRepository.save(pokemon);

        Pokemon pokemonSave = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSave.setPokemonName("Aboba");

        Pokemon pokemonUpdate = pokemonRepository.save(pokemonSave);

        Assertions.assertThat(pokemonUpdate.getPokemonName()).isNotNull();
        Assertions.assertThat(pokemonUpdate.getPokemonAttack()).isEqualTo(pokemonSave.getPokemonAttack());
    }

    @Test
    public void testDeletePokemon_whenValidPokemon_shouldReturnNull() {

        pokemonRepository.save(pokemon);

        pokemonRepository.delete(pokemon);
        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(pokemonOptional).isEmpty();
    }
}

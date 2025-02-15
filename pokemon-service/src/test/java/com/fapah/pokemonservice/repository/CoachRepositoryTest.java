package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import org.junit.jupiter.api.Assertions;
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
public class CoachRepositoryTest {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    private Coach coach;

    @BeforeEach
    void setup() {
        coach = Coach.builder()
                .coachName("Aboba")
                .coachsPokemons(null)
                .build();
    }

    @Test
    public void testGetAllCoachs_whenDatabaseHaveCoachs_shouldReturnAllCoachs() {

        Coach coach1 = Coach.builder()
                .coachName("Aboba")
                .coachsPokemons(null)
                .build();

        Coach coach2 = Coach.builder()
                .coachName("Jeff")
                .coachsPokemons(null)
                .build();

        coachRepository.save(coach1);
        coachRepository.save(coach2);

        List<Coach> coachs = coachRepository.findAll();

        Assertions.assertNotNull(coachs);
        Assertions.assertEquals(coachs.size(), 2);
    }

    @Test
    public void testGetCoachById_whenValidCoachId_shouldReturnCoach() {

        coachRepository.save(coach);

        Coach coachTest = coachRepository.findById(coach.getId()).get();

        Assertions.assertNotNull(coachTest);
        Assertions.assertEquals(coachTest.getId(), coach.getId());
    }

    @Test
    public void testGetCoachByName_whenValidCoachName_shouldReturnCoach() {

        coachRepository.save(coach);

        Coach coachTest = coachRepository.findByCoachName(coach.getCoachName()).get();

        Assertions.assertNotNull(coachTest);
        Assertions.assertEquals(coachTest.getCoachName(), coach.getCoachName());
    }

    @Test
    public void testAddCoach_whenValidCoach_shouldReturnCoach() {

        Coach savedCoach = coachRepository.save(coach);

        Assertions.assertNotNull(savedCoach);
        Assertions.assertEquals(savedCoach.getCoachName(), coach.getCoachName());
    }

    @Test
    public void testUpdateCoach_whenValidCoach_shouldReturnCoach() {

        Type pokemonType = Type.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();

        Pokemon pokemon = Pokemon.builder()
                .pokemonName("Pikachu")
                .pokemonType(new ArrayList<>(Arrays.asList(pokemonType)))
                .pokemonCoach(null)
                .pokemonHp(100)
                .pokemonAttack(100)
                .pokemonDefence(100)
                .pokemonHeight(100)
                .pokemonWeight(100)
                .build();


        Coach savedCoach = coachRepository.save(coach);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        savedCoach.setCoachsPokemons(new ArrayList<>(Arrays.asList(savedPokemon)));

        Coach updatedCoach = coachRepository.save(savedCoach);

        Assertions.assertNotNull(updatedCoach);
        Assertions.assertTrue(updatedCoach.getCoachsPokemons().contains(pokemon));
    }

    @Test
    public void testDeleteCoach_whenValidCoachId_shouldReturnNull() {

        Coach savedCoach = coachRepository.save(coach);

        coachRepository.delete(savedCoach);
        Optional<Coach> coachOptional = coachRepository.findById(savedCoach.getId());

        Assertions.assertTrue(coachOptional.isEmpty());
    }
}

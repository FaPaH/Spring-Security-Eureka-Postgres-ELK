package com.fapah.pokemonservice.repository;

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
public class TypeRepositoryTest {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    private Type type;

    @BeforeEach
    void setup() {
        type = Type.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();
    }

    @Test
    public void testAddType_whenValidType_shouldReturnExpectedType() {

        Type savedType = typeRepository.save(type);

        Assertions.assertNotNull(savedType);
        Assertions.assertEquals(savedType, type);
    }

    @Test
    public void testGetAllTypes_whenDatabaseHaveTypes_shouldReturnAllTypes() {
        Type type1 = Type.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();

        Type type2 = Type.builder()
                .typeName("Fire")
                .typeColor("#ggg")
                .build();

        typeRepository.save(type1);
        typeRepository.save(type2);

        List<Type> types = typeRepository.findAll();

        Assertions.assertNotNull(types);
        Assertions.assertEquals(types.size(), 2);
    }

    @Test
    public void testGetTypeById_whenValidId_shouldReturnExpectedType() {

        typeRepository.save(type);

        Type typeTest = typeRepository.findById(type.getId()).get();

        Assertions.assertNotNull(typeTest);
        Assertions.assertEquals(typeTest, type);
    }

    @Test
    public void testGetTypeById_whenValidName_shouldReturnExpectedType() {

        typeRepository.save(type);

        Type typeTest = typeRepository.findByTypeName(type.getTypeName()).get();

        Assertions.assertNotNull(typeTest);
        Assertions.assertEquals(typeTest, type);
    }

    @Test
    public void testUpdatePokemon_whenValidPokemon_shouldReturnExpectedPokemon() {

        typeRepository.save(type);

        Type typeSave = typeRepository.findById(type.getId()).get();
        typeSave.setTypeColor("#ggg");

        Type typeUpdate = typeRepository.save(typeSave);

        Assertions.assertNotNull(typeUpdate.getTypeColor());
        Assertions.assertEquals(typeUpdate.getTypeColor(), typeSave.getTypeColor());
    }

    @Test
    public void testDeleteType_whenValidType_shouldReturnNull() {

        typeRepository.save(type);

        typeRepository.delete(type);
        Optional<Type> typeOptional = typeRepository.findById(type.getId());

        Assertions.assertTrue(typeOptional.isEmpty());
    }
}

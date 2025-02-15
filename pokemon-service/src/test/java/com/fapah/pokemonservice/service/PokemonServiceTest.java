package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.repository.PokemonRepository;
import com.fapah.pokemonservice.service.impl.PokemonServiceImpl;
import com.fapah.pokemonservice.service.mapper.PokemonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    PokemonRepository pokemonRepository;

    @Mock
    PokemonMapper pokemonMapper;

    @InjectMocks
    PokemonServiceImpl pokemonService;

    private Pokemon pokemon;

    private PokemonDto pokemonDto;

    @BeforeEach
    void setup() {
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

        TypeDto pokemonTypeDto = TypeDto.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();

        pokemonDto = PokemonDto.builder()
                .pokemonName("Pikachu")
                .pokemonType(new ArrayList<>(Arrays.asList(pokemonTypeDto)))
                .pokemonCoachName(null)
                .pokemonHp(100)
                .pokemonAttack(100)
                .pokemonDefence(100)
                .pokemonHeight(100)
                .pokemonWeight(100)
                .build();
    }

    @Test
    public void testAddPokemon_whenValidPokemonDtoAndDataBaseDidntHavePokemon_shouldReturnPokemonDto() {

        given(pokemonRepository.findByPokemonName(pokemonDto.getPokemonName())).willReturn(Optional.empty());
        when(pokemonMapper.mapToDto(pokemon)).thenReturn(pokemonDto);
        when(pokemonMapper.mapToEntity(pokemonDto)).thenReturn(pokemon);
        given(pokemonRepository.saveAndFlush(pokemon)).willReturn(pokemon);

        PokemonDto savedPokemonDto = pokemonService.addPokemon(pokemonDto);

        Assertions.assertNotNull(savedPokemonDto);
        Assertions.assertEquals(savedPokemonDto, pokemonDto);
    }

    @Test
    public void testGetAllPokemonDtos_whenDatabaseHavePokemons_shouldReturnPokemonDtos() {

        List<Pokemon> pokemonList = new ArrayList<>(Arrays.asList(pokemon, pokemon));

        given(pokemonRepository.findAll()).willReturn(pokemonList);

        List<PokemonDto> pokemonDtoList = pokemonService.getAllPokemons();

        Assertions.assertNotNull(pokemonDtoList);
        Assertions.assertEquals(pokemonDtoList.size(), pokemonList.size());
    }

    @Test
    public void testGetPokemonById_whenValidPokemonIdAndDatabaseHavePokemon_shouldReturnPokemonDto() {

        given(pokemonRepository.findById(pokemon.getId())).willReturn(Optional.of(pokemon));
        when(pokemonMapper.mapToDto(pokemon)).thenReturn(pokemonDto);

        PokemonDto foundPokemonDto = pokemonService.getPokemonById(pokemon.getId());

        Assertions.assertNotNull(foundPokemonDto);
        Assertions.assertEquals(foundPokemonDto, pokemonDto);
    }

    @Test
    public void testGetPokemonByName_whenValidPokemonNameAndDatabaseHavePokemon_shouldReturnPokemonDto() {

        given(pokemonRepository.findByPokemonName(pokemon.getPokemonName())).willReturn(Optional.of(pokemon));
        when(pokemonMapper.mapToDto(pokemon)).thenReturn(pokemonDto);

        PokemonDto foundPokemonDto = pokemonService.getPokemonByName(pokemon.getPokemonName());

        Assertions.assertNotNull(foundPokemonDto);
        Assertions.assertEquals(foundPokemonDto, pokemonDto);
    }

    @Test
    public void testDeletePokemon_whenValidPokemonId_shouldVerifyDeleteMethodCall() {

        willDoNothing().given(pokemonRepository).deleteById(pokemon.getId());

        pokemonService.deletePokemon(pokemon.getId());

        verify(pokemonRepository, atLeastOnce()).deleteById(pokemon.getId());
    }
}

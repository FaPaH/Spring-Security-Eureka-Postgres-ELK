package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.service.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testAddPokemon_whenValidPokemon_shouldReturnOkPokemonDto() throws Exception {

        given(pokemonService.addPokemon(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/pokemon/addPokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonName").value(pokemon.getPokemonName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonType", hasSize(1)));
    }

    @Test
    public void testGetAllPokemonDtos_whenDatabaseHavePokemons_shouldReturnOkAllPokemonsDtos() throws Exception {

        List<PokemonDto> pokemonDtos = Arrays.asList(pokemonDto, pokemonDto);

        when(pokemonService.getAllPokemons()).thenReturn(pokemonDtos);

        ResultActions response = mockMvc.perform(get("/api/v1/pokemon/getAllPokemons")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(pokemonDtos.size())));
    }

    @Test
    public void testGetPokemonById_whenValidPokemonIdAndDatabaseHavePokemons_shouldReturnOkPokemonDtos() throws Exception {

        when(pokemonService.getPokemonById(pokemon.getId())).thenReturn(pokemonDto);

        ResultActions response = mockMvc.perform(get("/api/v1/pokemon/getPokemonById")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pokemonId", String.valueOf(pokemon.getId()))
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonName").value(pokemon.getPokemonName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonType", hasSize(1)));
    }

    @Test
    public void testGetPokemonByName_whenValidPokemonNameAndDatabaseHavePokemons_shouldReturnOkPokemonDtos() throws Exception {

        when(pokemonService.getPokemonByName(pokemon.getPokemonName())).thenReturn(pokemonDto);

        ResultActions response = mockMvc.perform(get("/api/v1/pokemon/getPokemonByName")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pokemonName", pokemon.getPokemonName())
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonName").value(pokemon.getPokemonName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonType", hasSize(1)));
    }

    @Test
    public void testDeletePokemon_whenValidPokemon_shouldReturnOk() throws Exception {

        doNothing().when(pokemonService).deletePokemon(pokemon.getId());

        ResultActions response = mockMvc.perform(delete("/api/v1/pokemon/deletePokemon")
                .param("pokemonId", String.valueOf(pokemon.getId()))
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}


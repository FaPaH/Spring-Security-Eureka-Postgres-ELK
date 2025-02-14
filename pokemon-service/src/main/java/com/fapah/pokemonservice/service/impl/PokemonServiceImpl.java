package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.*;
import com.fapah.pokemonservice.repository.PokemonRepository;
import com.fapah.pokemonservice.repository.TypeRepository;
import com.fapah.pokemonservice.service.PokemonService;
import com.fapah.pokemonservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final ModelMapper modelMapper;

    private final PokemonRepository pokemonRepository;

    private final TypeRepository typeRepository;

    @Override
    public List<PokemonDto> getAllPokemons() {
        try {

            List<Pokemon> pokemons = pokemonRepository.findAll();

            if(pokemons.isEmpty()) {
                return Collections.emptyList();
            }

        return pokemons.stream().map(this::mapToDto).toList();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "pokemon", key = "#pokemonId")
    public PokemonDto getPokemonById(long pokemonId) {
        try {

            Pokemon pokemon = pokemonRepository.findById(pokemonId)
                    .orElseThrow(
                            () -> new PokemonNotFoundException("Pokemon not found")
            );
            return mapToDto(pokemon);

        } catch (PokemonNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "pokemon", key = "#pokemonName")
    public PokemonDto getPokemonByName(String pokemonName) {
        try {

            Pokemon pokemon = pokemonRepository.findByPokemonName(pokemonName).orElseThrow(
                    () -> new PokemonNotFoundException("Pokemon not found")
            );
            return mapToDto(pokemon);

        } catch (PokemonNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @CachePut(value = "pokemon", key = "#pokemonDto.pokemonName")
    public PokemonDto addPokemon(PokemonDto pokemonDto) {
        try {

            if (pokemonRepository.findByPokemonName(pokemonDto.getPokemonName()).isPresent()) {
                throw new PokemonAlreadyExistException("Pokemon already exist");
            }

            return mapToDto(pokemonRepository.saveAndFlush(mapToEntity(pokemonDto)));

        } catch (PokemonAlreadyExistException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @CacheEvict(value = "pokemon", key = "#pokemonId")
    public String deletePokemon(long pokemonId) {
        try {

            pokemonRepository.deleteById(pokemonId);
            return "Pokemon deleted successfully";

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "pokemon", key = "#pokemonName")
    public boolean checkPokemonHaveCoach(String pokemonName) {
        return getPokemonByName(pokemonName).getPokemonCoachName() != null;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        try {
            Pokemon pokemon = modelMapper.map(pokemonDto, Pokemon.class);

            List<Type> types = new ArrayList<>();
            for (TypeDto typeDto : pokemonDto.getPokemonType()) {
                types.add(typeRepository.findByTypeName(typeDto.getTypeName()).get());
            }

            pokemon.setPokemonType(types);

            return pokemon;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    private PokemonDto mapToDto(Pokemon pokemon) {
        try {
            PokemonDto pokemonDto = modelMapper.map(pokemon, PokemonDto.class);
            pokemonDto.setPokemonCoachName(pokemon.getPokemonCoach().getCoachName());
            pokemonDto.setPokemonType(pokemon.getPokemonType()
                    .stream()
                    .map(p -> modelMapper.map(p, TypeDto.class))
                    .toList());

            return pokemonDto;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.exception.*;
import com.fapah.pokemonservice.repository.PokemonRepository;
import com.fapah.pokemonservice.service.PokemonService;
import com.fapah.pokemonservice.service.mapper.PokemonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    private final PokemonMapper pokemonMapper;

    @Override
    public List<PokemonDto> getAllPokemons() {
        try {

            List<Pokemon> pokemons = pokemonRepository.findAll();

            if(pokemons.isEmpty()) {
                log.info("No pokemons found");
                return Collections.emptyList();
            }

            log.info("Found {} pokemons", pokemons.size());
            return pokemons.stream().map(pokemonMapper::mapToDto).toList();

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

            log.info("Found pokemon {} by ID: {}", pokemon, pokemonId);
            return pokemonMapper.mapToDto(pokemon);

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

            log.info("Found pokemon {} by Name: {}", pokemon, pokemonName);
            return pokemonMapper.mapToDto(pokemon);

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

            log.info("Saving pokemon {}", pokemonDto);
            return pokemonMapper.mapToDto(pokemonRepository.saveAndFlush(pokemonMapper.mapToEntity(pokemonDto)));

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
    public void deletePokemon(long pokemonId) {
        try {

            log.info("Deleting pokemon {}", pokemonId);
            pokemonRepository.deleteById(pokemonId);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "pokemon", key = "#pokemonName")
    public boolean checkPokemonHaveCoach(String pokemonName) {
        boolean isPokemonHaveCoach = getPokemonByName(pokemonName).getPokemonCoachName() != null;
        log.info("Pokemon {} have coach: {}", pokemonName, isPokemonHaveCoach);
        return isPokemonHaveCoach;
    }
}

package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.exception.CoachAlreadyExistException;
import com.fapah.pokemonservice.exception.CoachNotFoundException;
import com.fapah.pokemonservice.exception.MapToException;
import com.fapah.pokemonservice.exception.PokemonAlreadyHaveCoachException;
import com.fapah.pokemonservice.repository.CoachRepository;
import com.fapah.pokemonservice.repository.PokemonRepository;
import com.fapah.pokemonservice.service.CoachService;
import com.fapah.pokemonservice.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final ModelMapper modelMapper;

    private final CoachRepository coachRepository;

    private final PokemonRepository pokemonRepository;

    @Override
    public List<CoachDto> getAllCoaches() {
        try {

            List<Coach> coaches = coachRepository.findAll();

            if(coaches.isEmpty()) {
                return Collections.emptyList();
            }

            return coaches.stream().map(this::mapToDto).toList();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public CoachDto getCoachById(long coachId) {
        try {

            Coach coach = coachRepository.findById(coachId)
                    .orElseThrow(
                            () -> new CoachNotFoundException("Coach not found")
                    );
            return mapToDto(coach);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public CoachDto getCoachByName(String coachName) {
        try {

            Coach coach = coachRepository.findByCoachName(coachName)
                    .orElseThrow(
                            () -> new CoachNotFoundException("Coach not found")
                    );
            return mapToDto(coach);

        } catch (CoachNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public String addCoach(CoachDto coachDto) {
        try {

            if(coachRepository.findByCoachName(coachDto.getCoachName()).isPresent()) {
                throw new CoachAlreadyExistException("Coach already exists");
            }

            Coach coach = mapToEntity(coachDto);
            if (coach.getCoachsPokemons() != null) {
                for (Pokemon pokemon : coach.getCoachsPokemons()) {
                    if (pokemon.getPokemonCoach() != null) {
                        throw new PokemonAlreadyHaveCoachException("Pokemon already have coach");
                    } else {
                        pokemon.setPokemonCoach(coach);
                    }
                }
            }
            coachRepository.saveAndFlush(coach);
            return "Coach added successfully";

        } catch (CoachAlreadyExistException | PokemonAlreadyHaveCoachException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public String deleteCoach(long coachId) {
        try {

            coachRepository.deleteById(coachId);
            return "Coach deleted successfully";

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    private CoachDto mapToDto(Coach coach) {
        try {

            return CoachDto.builder()
                    .coachName(coach.getCoachName())
                    .coachsPokemons(
                            coach.getCoachsPokemons()
                                    .stream()
                                    .map(p -> modelMapper.map(p, PokemonDto.class))
                                    .toList()
                    )
                    .build();

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    private Coach mapToEntity(CoachDto coachDto) {
        try {

            List<Pokemon> pokemons = new ArrayList<>();

            for (PokemonDto pokemonDto : coachDto.getCoachsPokemons()) {
                pokemons.add(pokemonRepository.findByPokemonName(pokemonDto.getPokemonName()).orElseThrow(RuntimeException::new));
            }

            return Coach.builder()
                    .coachName(coachDto.getCoachName())
                    .coachsPokemons(pokemons)
                    .build();

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

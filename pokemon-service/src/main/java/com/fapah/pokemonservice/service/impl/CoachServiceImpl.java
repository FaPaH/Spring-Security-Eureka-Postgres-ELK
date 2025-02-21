package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.exception.CoachAlreadyExistException;
import com.fapah.pokemonservice.exception.CoachNotFoundException;
import com.fapah.pokemonservice.exception.PokemonAlreadyHaveCoachException;
import com.fapah.pokemonservice.repository.CoachRepository;
import com.fapah.pokemonservice.service.CoachService;
import com.fapah.pokemonservice.service.mapper.CoachMapper;
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
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;

    private final CoachMapper coachMapper;

    @Override
    public List<CoachDto> getAllCoaches() {
        try {

            List<Coach> coaches = coachRepository.findAll();

            if(coaches.isEmpty()) {
                log.info("No coaches found");
                return Collections.emptyList();
            }

            log.info("Found {} coaches", coaches.size());
            return coaches.stream().map(coachMapper::mapToDto).toList();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "coach", key = "#coachId")
    public CoachDto getCoachById(long coachId) {
        try {

            Coach coach = coachRepository.findById(coachId)
                    .orElseThrow(
                            () -> new CoachNotFoundException("Coach not found")
                    );

            log.info("Found coach {} by ID: {}", coach, coachId);
            return coachMapper.mapToDto(coach);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "coach", key = "#coachName")
    public CoachDto getCoachByName(String coachName) {
        try {

            Coach coach = coachRepository.findByCoachName(coachName)
                    .orElseThrow(
                            () -> new CoachNotFoundException("Coach not found")
                    );

            log.info("Found coach {} by Name: {}", coach, coachName);
            return coachMapper.mapToDto(coach);

        } catch (CoachNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @CachePut(value = "coach", key = "#coachDto.coachName")
    public CoachDto addCoach(CoachDto coachDto) {
        try {

            if(coachRepository.findByCoachName(coachDto.getCoachName()).isPresent()) {
                throw new CoachAlreadyExistException("Coach already exists");
            }

            Coach coach = coachMapper.mapToEntity(coachDto);
            if (coach.getCoachsPokemons() != null) {
                for (Pokemon pokemon : coach.getCoachsPokemons()) {
                    if (pokemon.getPokemonCoach() != null) {
                        throw new PokemonAlreadyHaveCoachException("Pokemon already have coach");
                    } else {
                        pokemon.setPokemonCoach(coach);
                    }
                }
            }

            log.info("Saving coach {}", coachDto);
            return coachMapper.mapToDto(coachRepository.saveAndFlush(coach));

        } catch (CoachAlreadyExistException | PokemonAlreadyHaveCoachException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @CacheEvict(value = "coach", key = "#coachId")
    public void deleteCoach(long coachId) {
        try {

            log.info("Deleting coach {}", coachId);
            coachRepository.deleteById(coachId);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }
}

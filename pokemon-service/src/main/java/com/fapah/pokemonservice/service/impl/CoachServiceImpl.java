package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.repository.CoachRepository;
import com.fapah.pokemonservice.repository.PokemonRepository;
import com.fapah.pokemonservice.service.CoachService;
import com.fapah.pokemonservice.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Coach> coaches = coachRepository.findAll();
        return coaches.stream().map(this::mapToDto).toList();
    }

    @Override
    public CoachDto getCoachById(long coachId) {
        Coach coach = coachRepository.findById(coachId).orElseThrow(RuntimeException::new);
        return mapToDto(coach);
    }

    @Override
    public CoachDto getCoachByName(String coachName) {
        Coach coach = coachRepository.findByCoachName(coachName).orElseThrow(RuntimeException::new);
        return mapToDto(coach);
    }

    @Override
    public String addCoach(CoachDto coachDto) {
        Coach coach = mapToEntity(coachDto);
        if (coach.getCoachsPokemons() != null) {
            for (Pokemon pokemon : coach.getCoachsPokemons()) {
                if (pokemon.getPokemonCoach() != null) {
                    return "Pokemon " + pokemon.getPokemonName() + " already has coach";
                } else {
                    pokemon.setPokemonCoach(coach);
                }
            }
        }
        coachRepository.saveAndFlush(coach);
        return "Coach added successfully";
    }

    @Override
    public String deleteCoach(long coachId) {
        coachRepository.deleteById(coachId);
        return "Coach deleted successfully";
    }

    private CoachDto mapToDto(Coach coach) {
        return CoachDto.builder()
                .coachName(coach.getCoachName())
                .coachsPokemons(
                        coach.getCoachsPokemons()
                        .stream()
                        .map(p -> modelMapper.map(p, PokemonDto.class))
                        .toList()
                )
                .build();
    }

    private Coach mapToEntity(CoachDto coachDto) {
        List<Pokemon> pokemons = new ArrayList<>();

        for (PokemonDto pokemonDto : coachDto.getCoachsPokemons()) {
            pokemons.add(pokemonRepository.findByPokemonName(pokemonDto.getPokemonName()).orElseThrow(RuntimeException::new));
        }

        return Coach.builder()
                .coachName(coachDto.getCoachName())
                .coachsPokemons(pokemons)
                .build();
    }
}

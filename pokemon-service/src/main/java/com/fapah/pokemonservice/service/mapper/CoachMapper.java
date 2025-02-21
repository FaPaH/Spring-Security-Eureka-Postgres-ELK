package com.fapah.pokemonservice.service.mapper;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.exception.MapToException;
import com.fapah.pokemonservice.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CoachMapper {

    private final ModelMapper modelMapper;

    private final PokemonRepository pokemonRepository;

    public CoachDto mapToDto(Coach coach) {
        try {

            log.info("Mapping Coach {} to CoachDto", coach);
            CoachDto coachDto = CoachDto.builder()
                    .coachName(coach.getCoachName())
                    .coachsPokemons(
                            coach.getCoachsPokemons()
                                    .stream()
                                    .map(p -> modelMapper.map(p, PokemonDto.class))
                                    .toList()
                    )
                    .build();

            log.info("Return mapped coachDto {}", coachDto);
            return coachDto;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    public Coach mapToEntity(CoachDto coachDto) {
        try {

            log.info("Mapping CoachDto {} to Coach", coachDto);
            List<Pokemon> pokemons = new ArrayList<>();

            for (PokemonDto pokemonDto : coachDto.getCoachsPokemons()) {
                pokemons.add(pokemonRepository.findByPokemonName(pokemonDto.getPokemonName()).orElseThrow(RuntimeException::new));
            }

            Coach coach = Coach.builder()
                    .coachName(coachDto.getCoachName())
                    .coachsPokemons(pokemons)
                    .build();

            log.info("Return mapped coach {}", coach);
            return coach;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

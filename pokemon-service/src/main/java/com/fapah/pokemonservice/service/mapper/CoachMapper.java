package com.fapah.pokemonservice.service.mapper;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.exception.MapToException;
import com.fapah.pokemonservice.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CoachMapper {

    private final ModelMapper modelMapper;

    private final PokemonRepository pokemonRepository;

    public CoachDto mapToDto(Coach coach) {
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

    public Coach mapToEntity(CoachDto coachDto) {
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

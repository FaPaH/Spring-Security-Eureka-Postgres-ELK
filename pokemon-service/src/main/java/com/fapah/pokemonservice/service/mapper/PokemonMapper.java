package com.fapah.pokemonservice.service.mapper;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.MapToException;
import com.fapah.pokemonservice.repository.TypeRepository;
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
public class PokemonMapper {

    private final ModelMapper modelMapper;

    private final TypeRepository typeRepository;

    public Pokemon mapToEntity(PokemonDto pokemonDto) {
        try {

            log.info("Mapping PokemonDto {} to Pokemon", pokemonDto);
            Pokemon pokemon = modelMapper.map(pokemonDto, Pokemon.class);

            List<Type> types = new ArrayList<>();
            for (TypeDto typeDto : pokemonDto.getPokemonType()) {
                types.add(typeRepository.findByTypeName(typeDto.getTypeName()).get());
            }

            pokemon.setPokemonType(types);

            log.info("Return mapped pokemon {}", pokemon);
            return pokemon;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    public PokemonDto mapToDto(Pokemon pokemon) {
        try {

            log.info("Mapping Pokemon {} to PokemonDto", pokemon);
            PokemonDto pokemonDto = modelMapper.map(pokemon, PokemonDto.class);

            if (pokemon.getPokemonCoach() == null) {
                pokemonDto.setPokemonCoachName("");
            } else {
                pokemonDto.setPokemonCoachName(pokemon.getPokemonCoach().getCoachName());
            }
            pokemonDto.setPokemonType(pokemon.getPokemonType()
                    .stream()
                    .map(p -> modelMapper.map(p, TypeDto.class))
                    .toList());

            log.info("Return mapped pokemonDto {}", pokemonDto);
            return pokemonDto;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

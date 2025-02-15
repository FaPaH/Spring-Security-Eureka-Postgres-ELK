package com.fapah.pokemonservice.service.mapper;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.MapToException;
import com.fapah.pokemonservice.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PokemonMapper {

    private final ModelMapper modelMapper;

    private final TypeRepository typeRepository;

    public Pokemon mapToEntity(PokemonDto pokemonDto) {
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

    public PokemonDto mapToDto(Pokemon pokemon) {
        try {
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

            return pokemonDto;

        } catch (MappingException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

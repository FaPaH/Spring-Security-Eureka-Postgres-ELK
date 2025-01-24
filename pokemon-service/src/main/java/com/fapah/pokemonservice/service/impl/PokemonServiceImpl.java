package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.repository.PokemonRepository;
import com.fapah.pokemonservice.repository.TypeRepository;
import com.fapah.pokemonservice.service.PokemonService;
import com.fapah.pokemonservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        List<Pokemon> content = pokemonRepository.findAll();
        return content.stream().map(this::mapToDto).toList();
    }

    @Override
    public PokemonDto getPokemonById(long pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(RuntimeException::new);
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto getPokemonByName(String pokemonName) {
        Pokemon pokemon = pokemonRepository.findByPokemonName(pokemonName).orElseThrow(RuntimeException::new);
        return mapToDto(pokemon);
    }

    @Override
    public String addPokemon(PokemonDto pokemonDto) {

        pokemonRepository.saveAndFlush(mapToEntity(pokemonDto));
        return "Pokemon added successfully";
    }

    @Override
    public String deletePokemon(long pokemonId) {
        pokemonRepository.deleteById(pokemonId);
        return "Pokemon deleted successfully";
    }

    @Override
    public boolean checkPokemonHaveCoach(String pokemonName) {
        return pokemonRepository.checkPokemonCoach(pokemonName).isPresent();
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = modelMapper.map(pokemonDto, Pokemon.class);

        List<Type> types = new ArrayList<>();
        for (TypeDto typeDto : pokemonDto.getPokemonType()) {
            types.add(typeRepository.findByTypeName(typeDto.getTypeName()).get());
        }

        pokemon.setPokemonType(types);

        return pokemon;
    }

    private PokemonDto mapToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = modelMapper.map(pokemon, PokemonDto.class);
        pokemonDto.setPokemonType(pokemon.getPokemonType()
                .stream()
                .map(p -> modelMapper.map(p, TypeDto.class))
                .toList());
        return pokemonDto;
    }
}

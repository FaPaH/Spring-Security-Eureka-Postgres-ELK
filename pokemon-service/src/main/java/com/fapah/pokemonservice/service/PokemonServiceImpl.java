package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final ModelMapper modelMapper;

    private final PokemonRepository pokemonRepository;

    @Override
    public List<Pokemon> getAllPokemons() {
        return pokemonRepository.findAll();
    }

    @Override
    public PokemonDto getPokemonById(long pokemonId) {
        return modelMapper.map(pokemonRepository.findById(pokemonId), PokemonDto.class);
    }

    @Override
    public String addPokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
        return "Pokemon added successfully";
    }

    @Override
    public String deletePokemon(long pokemonId) {
        pokemonRepository.deleteById(pokemonId);
        return "Pokemon deleted successfully";
    }

    @Override
    public PokemonDto getPokemonByName(String pokemonName) {
        return modelMapper.map(pokemonRepository.findByPokemomName(pokemonName), PokemonDto.class);
    }
}

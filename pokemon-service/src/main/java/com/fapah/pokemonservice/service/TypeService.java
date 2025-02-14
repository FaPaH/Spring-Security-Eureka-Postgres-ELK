package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;

import java.util.List;

public interface TypeService {

    List<TypeDto> getAllTypes();

    TypeDto getTypeById(long typeId);

    TypeDto getTypeByName(String typeName);

    TypeDto addType(TypeDto typeDto);

    String deleteType(long typeId);
}

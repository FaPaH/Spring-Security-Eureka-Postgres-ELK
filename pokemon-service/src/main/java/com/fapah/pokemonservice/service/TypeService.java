package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;

import java.util.List;

public interface TypeService {

    List<Type> getAllTypes();

    TypeDto getTypeById(long typeId);

    TypeDto getTypeByName(String typeName);

    String addType(Type type);

    String deleteType(long typeId);
}

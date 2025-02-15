package com.fapah.pokemonservice.service.mapper;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.MapToException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeMapper {

    private final ModelMapper modelMapper;

    public TypeDto mapToDto(Type type) {
        try {
            return modelMapper.map(type, TypeDto.class);
        } catch (MapToException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    public Type mapToEntity(TypeDto typeDto) {
        try {
            return modelMapper.map(typeDto, Type.class);
        } catch (MapToException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

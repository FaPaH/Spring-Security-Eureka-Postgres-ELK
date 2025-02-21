package com.fapah.pokemonservice.service.mapper;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.MapToException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TypeMapper {

    private final ModelMapper modelMapper;

    public TypeDto mapToDto(Type type) {
        try {

            log.info("Mapping Type {} to TypeDto", type);
            TypeDto typeDto = modelMapper.map(type, TypeDto.class);

            log.info("Return mapped typeDto {}", typeDto);
            return typeDto;
        } catch (MapToException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    public Type mapToEntity(TypeDto typeDto) {
        try {

            log.info("Mapping TypeDto {} to Type", typeDto);
            Type type = modelMapper.map(typeDto, Type.class);

            log.info("Return mapped type {}", type);
            return type;
        } catch (MapToException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

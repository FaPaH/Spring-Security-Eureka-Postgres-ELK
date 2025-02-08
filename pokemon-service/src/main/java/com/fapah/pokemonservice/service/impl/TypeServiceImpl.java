package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.*;
import com.fapah.pokemonservice.repository.TypeRepository;
import com.fapah.pokemonservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<TypeDto> getAllTypes() {
        try {

            List<Type> types = typeRepository.findAll();

            if (types.isEmpty()) {
                return Collections.emptyList();
            }

            return types.stream().map(this::mapToDto).toList();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public TypeDto getTypeById(long typeId) {
        try {

            Type type = typeRepository.findById(typeId)
                    .orElseThrow(
                            () -> new TypeNotFoundException("Type not found")
                    );
            return mapToDto(type);

        } catch (TypeNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public TypeDto getTypeByName(String typeName) {
        try {

            Type type = typeRepository.findByTypeName(typeName)
                    .orElseThrow(
                            () -> new TypeNotFoundException("Type not found")
                    );
            return mapToDto(type);

        } catch (TypeNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public String addType(TypeDto typeDto) {
        try {

            if (typeRepository.findByTypeName(typeDto.getTypeName()).isPresent()) {
                throw new TypeAlreadyExistException("Type already exist");
            }

            typeRepository.saveAndFlush(mapToEntity(typeDto));
            return "Type added successfully";

        } catch (TypeAlreadyExistException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    public String deleteType(long typeId) {
        try {

            typeRepository.deleteById(typeId);
            return "Type deleted successfully";

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    private TypeDto mapToDto(Type type) {
        try {
            return modelMapper.map(type, TypeDto.class);
        } catch (MapToException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }

    private Type mapToEntity(TypeDto typeDto) {
        try {
            return modelMapper.map(typeDto, Type.class);
        } catch (MapToException e) {
            throw new MapToException("Uncaught exception, please try later");
        }
    }
}

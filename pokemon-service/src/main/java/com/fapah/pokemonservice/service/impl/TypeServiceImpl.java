package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.exception.*;
import com.fapah.pokemonservice.repository.TypeRepository;
import com.fapah.pokemonservice.service.TypeService;
import com.fapah.pokemonservice.service.mapper.TypeMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    @Override
    @Cacheable(value = "type")
    public List<TypeDto> getAllTypes() {
        try {

            List<Type> types = typeRepository.findAll();

            if (types.isEmpty()) {
                return Collections.emptyList();
            }

            return types.stream().map(typeMapper::mapToDto).toList();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "type", key = "#typeId")
    public TypeDto getTypeById(long typeId) {
        try {

            Type type = typeRepository.findById(typeId)
                    .orElseThrow(
                            () -> new TypeNotFoundException("Type not found")
                    );
            return typeMapper.mapToDto(type);

        } catch (TypeNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @Cacheable(value = "type", key = "#typeName")
    public TypeDto getTypeByName(String typeName) {
        try {

            Type type = typeRepository.findByTypeName(typeName)
                    .orElseThrow(
                            () -> new TypeNotFoundException("Type not found")
                    );
            return typeMapper.mapToDto(type);

        } catch (TypeNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @CachePut(value = "type", key = "#typeDto.typeName")
    public TypeDto addType(TypeDto typeDto) {
        try {

            if (typeRepository.findByTypeName(typeDto.getTypeName()).isPresent()) {
                throw new TypeAlreadyExistException("Type already exist");
            }

            return typeMapper.mapToDto(typeRepository.saveAndFlush(typeMapper.mapToEntity(typeDto)));

        } catch (TypeAlreadyExistException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }

    @Override
    @CacheEvict(value = "type", key = "#typeId")
    public void deleteType(long typeId) {
        try {

            typeRepository.deleteById(typeId);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Value can`t be null");
        } catch (RuntimeException e) {
            throw new RuntimeException("Uncaught exception, please try later", e);
        }
    }
}

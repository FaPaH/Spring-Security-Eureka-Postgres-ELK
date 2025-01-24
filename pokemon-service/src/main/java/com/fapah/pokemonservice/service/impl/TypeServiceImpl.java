package com.fapah.pokemonservice.service.impl;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.repository.TypeRepository;
import com.fapah.pokemonservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<TypeDto> getAllTypes() {
        List<Type> types = typeRepository.findAll();
        return types.stream().map(this::mapToDto).toList();
    }

    @Override
    public TypeDto getTypeById(long typeId) {
        Type type = typeRepository.findById(typeId).orElseThrow(RuntimeException::new);
        return mapToDto(type);
    }

    @Override
    public TypeDto getTypeByName(String typeName) {
        Type type = typeRepository.findByTypeName(typeName).orElseThrow(RuntimeException::new);
        return mapToDto(type);
    }

    @Override
    public String addType(TypeDto typeDto) {
        typeRepository.saveAndFlush(mapToEntity(typeDto));
        return "Type added successfully";
    }

    @Override
    public String deleteType(long typeId) {
        typeRepository.deleteById(typeId);
        return "Type deleted successfully";
    }

    private TypeDto mapToDto(Type type) {
        return modelMapper.map(type, TypeDto.class);
    }

    private Type mapToEntity(TypeDto typeDto) {
        return modelMapper.map(typeDto, Type.class);
    }
}

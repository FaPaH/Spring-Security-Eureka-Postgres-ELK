package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.repository.TypeRepository;
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
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public TypeDto getTypeById(long typeId) {
        return modelMapper.map(typeRepository.findById(typeId), TypeDto.class);
    }

    @Override
    public TypeDto getTypeByName(String typeName) {
        return modelMapper.map(typeRepository.findByTypeName(typeName), TypeDto.class);
    }

    @Override
    public String addType(Type type) {
        typeRepository.save(type);
        return "Type added successfully";
    }

    @Override
    public String deleteType(long typeId) {
        typeRepository.deleteById(typeId);
        return "Type deleted successfully";
    }
}

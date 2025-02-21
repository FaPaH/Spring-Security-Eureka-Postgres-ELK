package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.repository.TypeRepository;
import com.fapah.pokemonservice.service.impl.TypeServiceImpl;
import com.fapah.pokemonservice.service.mapper.TypeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TypeMapper typeMapper;

    @InjectMocks
    private TypeServiceImpl typeService;

    private Type type;

    private TypeDto typeDto;

    @BeforeEach
    void setup() {
        type = Type.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();

        typeDto = TypeDto.builder()
                .typeName("Electric")
                .typeColor("#fff")
                .build();
    }

    @Test
    public void testAddType_whenValidTypeDtoAndDataBaseDidntHaveType_shouldReturnTypeDto() {

        given(typeRepository.findByTypeName(typeDto.getTypeName())).willReturn(Optional.empty());
        when(typeMapper.mapToDto(Mockito.any(Type.class))).thenReturn(typeDto);
        when(typeMapper.mapToEntity(Mockito.any(TypeDto.class))).thenReturn(type);
        given(typeRepository.saveAndFlush(type)).willReturn(type);

        TypeDto savedTypeDto = typeService.addType(typeDto);

        Assertions.assertNotNull(savedTypeDto);
        Assertions.assertEquals(savedTypeDto, typeDto);
    }

    @Test
    public void testGetAllTypeDtos_whenDatabaseHaveType_shouldReturnAllTypeDtos() {

        List<Type> typeList = new ArrayList<>(Arrays.asList(type, type));

        given(typeRepository.findAll()).willReturn(typeList);
        when(typeMapper.mapToDto(Mockito.any(Type.class))).thenReturn(typeDto);

        List<TypeDto> typeDtos = typeService.getAllTypes();

        Assertions.assertNotNull(typeDtos);
        Assertions.assertEquals(typeDtos.size(), typeList.size());
    }

    @Test
    public void testGetTypeById_whenValidTypeIdAndDatabaseHaveType_shouldReturnTypeDto() {

        given(typeRepository.findById(type.getId())).willReturn(Optional.of(type));
        when(typeMapper.mapToDto(Mockito.any(Type.class))).thenReturn(typeDto);

        TypeDto foundTypeDto = typeService.getTypeById(type.getId());

        Assertions.assertNotNull(foundTypeDto);
        Assertions.assertEquals(foundTypeDto, typeDto);
    }

    @Test
    public void testGetTypeByName_whenValidTypeNameAndDatabaseHaveType_shouldReturnTypeDto() {

        given(typeRepository.findByTypeName(type.getTypeName())).willReturn(Optional.of(type));
        when(typeMapper.mapToDto(Mockito.any(Type.class))).thenReturn(typeDto);

        TypeDto foundTypeDto = typeService.getTypeByName(type.getTypeName());

        Assertions.assertNotNull(foundTypeDto);
        Assertions.assertEquals(foundTypeDto, typeDto);
    }

    @Test
    public void testDeleteType_whenValidPokemonId_shouldVerifyDeleteMethodCall() {

        willDoNothing().given(typeRepository).deleteById(type.getId());

        typeService.deleteType(type.getId());

        verify(typeRepository, atLeastOnce()).deleteById(type.getId());
    }
}

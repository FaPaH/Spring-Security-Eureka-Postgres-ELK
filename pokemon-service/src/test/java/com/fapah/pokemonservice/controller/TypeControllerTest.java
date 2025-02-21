package com.fapah.pokemonservice.controller;


import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.service.PokemonService;
import com.fapah.pokemonservice.service.TypeService;
import com.fapah.pokemonservice.service.impl.TypeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeServiceImpl typeService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testAddType_whenValidType_shouldReturnOkPokemonDto() throws Exception {
        given(typeService.addType(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/type/addType")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeName").value(type.getTypeName()));
    }

    @Test
    public void testGetAllType_whenDatabaseHaveTypes_shouldReturnOkAllTypeDtos() throws Exception {
        List<TypeDto> typeDtos = Arrays.asList(typeDto, typeDto);

        given(typeService.getAllTypes()).willReturn(typeDtos);

        ResultActions response = mockMvc.perform(get("/api/v1/type/getAllType"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(typeDtos.size())));
    }

    @Test
    public void testGetTypeById_whenValidTypeIdAndDatabaseHaveTypes_shouldReturnOkTypeDto() throws Exception {

        when(typeService.getTypeById(type.getId())).thenReturn(typeDto);

        ResultActions response = mockMvc.perform(get("/api/v1/type/getTypeById")
                .contentType(MediaType.APPLICATION_JSON)
                .param("typeId", String.valueOf(type.getId()))
                .content(objectMapper.writeValueAsString(typeDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeName").value(type.getTypeName()));
    }

    @Test
    public void testGetTypeByName_whenValidTypeNameAndDatabaseHaveTypes_shouldReturnOkTypeDto() throws Exception {

        when(typeService.getTypeByName(type.getTypeName())).thenReturn(typeDto);

        ResultActions response = mockMvc.perform(get("/api/v1/type/getTypeByName")
                .contentType(MediaType.APPLICATION_JSON)
                .param("typeName", type.getTypeName())
                .content(objectMapper.writeValueAsString(typeDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeName").value(type.getTypeName()));
    }

    @Test
    public void testDeleteType_whenValidType_shouldReturnOk() throws Exception {

        doNothing().when(typeService).deleteType(type.getId());

        ResultActions response = mockMvc.perform(delete("/api/v1/type/deleteType")
                .param("typeId", String.valueOf(type.getId()))
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}

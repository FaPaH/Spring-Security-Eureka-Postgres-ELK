package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.service.impl.CoachServiceImpl;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CoachController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CoachControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CoachServiceImpl coachService;

    @Autowired
    private ObjectMapper objectMapper;

    private Coach coach;

    private CoachDto coachDto;

    @BeforeEach
    void setup() {
        coach = Coach.builder()
                .coachName("Aboba")
                .coachsPokemons(null)
                .build();

        coachDto = CoachDto.builder()
                .coachName("Aboba")
                .coachsPokemons(null)
                .build();
    }

    @Test
    public void testAddCoach_whenValidCoach_shouldReturnOkCoachDto() throws Exception {
        given(coachService.addCoach(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/coach/addCoach")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coachDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coachName").value(coach.getCoachName()));
    }

    @Test
    public void testGetAllCoach_whenDatabaseHaveCoaches_shouldReturnOkAllCoachDtos() throws Exception {
        List<CoachDto> coachDtos = Arrays.asList(coachDto, coachDto);

        given(coachService.getAllCoaches()).willReturn(coachDtos);

        ResultActions response = mockMvc.perform(get("/api/v1/coach/getAllCoaches"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(coachDtos.size())));
    }

    @Test
    public void testGetCoachById_whenValidCoachIdAndDatabaseHaveCoaches_shouldReturnOkCoachDto() throws Exception {

        when(coachService.getCoachById(coach.getId())).thenReturn(coachDto);

        ResultActions response = mockMvc.perform(get("/api/v1/coach/getCoachById")
                .contentType(MediaType.APPLICATION_JSON)
                .param("coachId", String.valueOf(coach.getId()))
                .content(objectMapper.writeValueAsString(coachDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coachName").value(coach.getCoachName()));
    }

    @Test
    public void testGetCoachByName_whenValidCoachNameAndDatabaseHaveCoaches_shouldReturnOkCoachDto() throws Exception {

        when(coachService.getCoachByName(coach.getCoachName())).thenReturn(coachDto);

        ResultActions response = mockMvc.perform(get("/api/v1/coach/getCoachByName")
                .contentType(MediaType.APPLICATION_JSON)
                .param("coachName", coach.getCoachName())
                .content(objectMapper.writeValueAsString(coachDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coachName").value(coach.getCoachName()));
    }

    @Test
    public void testDeleteCoach_whenValidCoache_shouldReturnOk() throws Exception {

        doNothing().when(coachService).deleteCoach(coach.getId());

        ResultActions response = mockMvc.perform(delete("/api/v1/coach/deleteCoach")
                .param("coachId", String.valueOf(coach.getId()))
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}

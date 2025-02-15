package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.repository.CoachRepository;
import com.fapah.pokemonservice.service.impl.CoachServiceImpl;
import com.fapah.pokemonservice.service.mapper.CoachMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoachServiceTest {

    @Mock
    private CoachRepository coachRepository;

    @Mock
    private CoachMapper coachMapper;

    @InjectMocks
    private CoachServiceImpl coachService;

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
    public void testAddCoach_whenValidCoach_shouldReturnCoachDto() {

        given(coachRepository.findByCoachName(coachDto.getCoachName())).willReturn(Optional.empty());
        when(coachMapper.mapToDto(coach)).thenReturn(coachDto);
        when(coachMapper.mapToEntity(coachDto)).thenReturn(coach);
        given(coachRepository.saveAndFlush(coach)).willReturn(coach);

        CoachDto savedCoachDto = coachService.addCoach(coachDto);

        Assertions.assertNotNull(savedCoachDto);
        Assertions.assertEquals(savedCoachDto, coachDto);
    }

    @Test
    public void testGetAllCoachDtos_whenDatabaseHaveCoach_shouldReturnCoachDtos() {

        List<Coach> coachList = new ArrayList<>(Arrays.asList(coach, coach));

        given(coachRepository.findAll()).willReturn(coachList);
        when(coachMapper.mapToDto(coach)).thenReturn(coachDto);

        List<CoachDto> coachDtoList = coachService.getAllCoaches();

        Assertions.assertNotNull(coachDtoList);
        Assertions.assertEquals(coachDtoList.size(), coachList.size());
    }

    @Test
    public void testGetCoachById_whenValidCoachIdAndDatabaseHaveCoach_shouldReturnCoachDto() {

        given(coachRepository.findById(coach.getId())).willReturn(Optional.of(coach));
        when(coachMapper.mapToDto(coach)).thenReturn(coachDto);

        CoachDto savedCoachDto = coachService.getCoachById(coach.getId());

        Assertions.assertNotNull(savedCoachDto);
        Assertions.assertEquals(savedCoachDto, coachDto);
    }

    @Test
    public void testGetCoachByName_whenValidCoachNameAndDatabaseHaveCoach_shouldReturnCoachDto() {

        given(coachRepository.findByCoachName(coach.getCoachName())).willReturn(Optional.of(coach));
        when(coachMapper.mapToDto(coach)).thenReturn(coachDto);

        CoachDto savedCoachDto = coachService.getCoachByName(coach.getCoachName());

        Assertions.assertNotNull(savedCoachDto);
        Assertions.assertEquals(savedCoachDto, coachDto);
    }

    @Test
    public void testDeleteCoach_whenValidCoachId_shouldVerifyDeleteMethodCall() {

        willDoNothing().given(coachRepository).deleteById(coach.getId());

        coachService.deleteCoach(coach.getId());

        verify(coachRepository, atLeastOnce()).deleteById(coach.getId());
    }
}

package com.fapah.pokemonservice.service;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final ModelMapper modelMapper;

    private final CoachRepository coachRepository;


    @Override
    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    @Override
    public CoachDto getCoachById(long coachId) {
        return modelMapper.map(coachRepository.findById(coachId), CoachDto.class);
    }

    @Override
    public CoachDto getCoachByName(String coachName) {
        return modelMapper.map(coachRepository.findByCoachName(coachName), CoachDto.class);
    }

    @Override
    public String addCoach(Coach coach) {
        coachRepository.save(coach);
        return "Coach added successfully";
    }

    @Override
    public String deleteCoach(long coachId) {
        coachRepository.deleteById(coachId);
        return "Coach deleted successfully";
    }
}

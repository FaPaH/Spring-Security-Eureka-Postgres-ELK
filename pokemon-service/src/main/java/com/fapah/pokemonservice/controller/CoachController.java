package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.CoachDto;
import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coach")
@RequiredArgsConstructor
@Slf4j
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/getAllCoaches")
    public ResponseEntity<List<CoachDto>> getAllCoaches() {
        log.info("Getting all Coaches");
        return ResponseEntity.ok().body(coachService.getAllCoaches());
    }

    @GetMapping("/getCoachById")
    public ResponseEntity<CoachDto> getCoachById(@RequestParam(name = "coachId") Long coachId) {
        log.info("Getting Coach by ID: {}", coachId);
        return ResponseEntity.ok().body(coachService.getCoachById(coachId));
    }

    @GetMapping("/getCoachByName")
    public ResponseEntity<CoachDto> getCoachByName(@RequestParam(name = "coachName") String coachName) {
        log.info("Getting Coach by name: {}", coachName);
        return ResponseEntity.ok().body(coachService.getCoachByName(coachName));
    }

    @PostMapping("/addCoach")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CoachDto> addCoach(@RequestBody @Valid CoachDto coachDto) {
        log.info("Adding Coach: {}", coachDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(coachService.addCoach(coachDto));
    }

    @DeleteMapping("/deleteCoach")
    public ResponseEntity<String> deleteCoach(@RequestParam(name = "coachId") Long coachId) {
        log.info("Deleting Coach by ID: {}", coachId);
        coachService.deleteCoach(coachId);
        return ResponseEntity.ok().body("Coach deleted successfully");
    }
}

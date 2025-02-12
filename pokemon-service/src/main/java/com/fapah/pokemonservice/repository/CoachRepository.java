package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByCoachName(String coachName);
}

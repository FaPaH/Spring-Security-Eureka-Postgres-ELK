package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Coach findByCoachName(String coachName);
}

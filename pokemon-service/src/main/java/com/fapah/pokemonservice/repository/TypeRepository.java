package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findByTypeName(String name);
}

package com.fapah.pokemonservice.repository;

import com.fapah.pokemonservice.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByTypeName(String name);
}

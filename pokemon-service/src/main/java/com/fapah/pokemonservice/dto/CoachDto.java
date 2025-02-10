package com.fapah.pokemonservice.dto;

import com.fapah.pokemonservice.entity.Pokemon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoachDto implements Serializable {

    @NotBlank(message = "Coach name can`t be blank")
    private String coachName;

    private List<PokemonDto> coachsPokemons;
}

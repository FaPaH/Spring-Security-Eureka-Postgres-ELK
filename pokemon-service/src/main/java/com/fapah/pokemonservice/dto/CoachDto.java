package com.fapah.pokemonservice.dto;

import com.fapah.pokemonservice.entity.Pokemon;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoachDto {

    @NotBlank(message = "Coach name can`t be blank")
    private String coachName;

    private List<PokemonDto> coachPokemons;
}

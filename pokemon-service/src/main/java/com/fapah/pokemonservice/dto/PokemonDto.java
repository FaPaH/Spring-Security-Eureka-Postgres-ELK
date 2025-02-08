package com.fapah.pokemonservice.dto;

import com.fapah.pokemonservice.entity.Coach;
import com.fapah.pokemonservice.entity.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PokemonDto {

    @NotBlank(message = "Pokemon name can`t be blank")
    private String pokemonName;

    @NotNull(message = "Pokemon hp can`t be null")
    @Min(value = 1, message = "Pokemon hp can`t be less than 1")
    private int pokemonHp;

    @NotNull(message = "Pokemon attack can`t be empty")
    @Min(value = 1, message = "Pokemon attack can`t be less than 1")
    private int pokemonAttack;

    @NotNull(message = "Pokemon defence can`t be empty")
    private int pokemonDefence;

    @NotNull(message = "Pokemon height can`t be empty")
    @Positive(message = "Pokemon weight can`t be less than 0")
    private float pokemonHeight;

    @NotNull(message = "Pokemon weight can`t be empty")
    @Positive(message = "Pokemon weight can`t be less than 0")
    private float pokemonWeight;

    @NotNull(message = "Pokemon type can`t be null")
    private List<TypeDto> pokemonType;

    private String pokemonCoachName;

}

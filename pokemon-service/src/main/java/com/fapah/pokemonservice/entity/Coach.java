package com.fapah.pokemonservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "coach")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "coach_name", nullable = false, unique = true)
    private String coachName;

    @Column(name = "coachs_pokemons")
    @OneToMany(mappedBy = "pokemonCoach", cascade = CascadeType.REFRESH)
    private List<Pokemon> coachPokemons;
}

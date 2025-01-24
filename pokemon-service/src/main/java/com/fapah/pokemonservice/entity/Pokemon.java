package com.fapah.pokemonservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pokemom_name", nullable = false, unique = true)
    private String pokemomName;

    @Column(name = "pokemon_hp", nullable = false)
    private int pokemonHp;

    @Column(name = "pokemon_attack", nullable = false)
    private int pokemonAttack;

    @Column(name = "pokemon_defence", nullable = false)
    private int pokemonDefence;

    @Column(name = "pokemon_height", nullable = false)
    private float pokemonHeight;

    @Column(name = "pokemon_weight", nullable = false)
    private float pokemonWeight;

    @Column(name = "pokemon_type", nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Type> type;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Coach pokemonCoach;
}

package com.fapah.pokemonservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import java.util.Set;

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

    @Column(name = "pokemon_name", nullable = false, unique = true)
    private String pokemonName;

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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "pokemon_type",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<Type> pokemonType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coach pokemonCoach;
}

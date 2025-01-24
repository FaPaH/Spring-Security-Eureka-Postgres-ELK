package com.fapah.pokemonservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    @Column(name = "type_color", nullable = false, unique = true)
    private String typeColor;

    @ManyToMany(mappedBy = "pokemonType", cascade = CascadeType.ALL)
    private List<Pokemon> typePokemoms;

}

package com.fapah.pokemonservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeDto {

    @NotBlank(message = "Type name can`t be blank")
    private String typeName;

    @NotBlank(message = "Type color can`t be blank")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Type color must be in a HEX format. Example: #000/#fff or #111111/#dddddd")
    private String typeColor;
}

package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.service.PokemonService;
import com.fapah.pokemonservice.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping("/getAllType")
    public ResponseEntity<List<Type>> getAllTypes(){
        return ResponseEntity.ok().body(typeService.getAllTypes());
    }

    @GetMapping("/getTypeById")
    public ResponseEntity<TypeDto> getTypeById(@RequestParam(name = "typeId") long typeId){
        return ResponseEntity.ok().body(typeService.getTypeById(typeId));
    }

    @PostMapping("/addType")
    public ResponseEntity<String> addType(@RequestBody @Valid Type type){
        return ResponseEntity.ok().body(typeService.addType(type));
    }

    @PostMapping("/deleteType")
    public ResponseEntity<String> deleteType(@RequestParam(name = "typeId") long typeId){
        return ResponseEntity.ok().body(typeService.deleteType(typeId));
    }
}

package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.service.PokemonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/getAllPokemons")
    public ResponseEntity<List<Pokemon>> getAllPokemons(){
        return ResponseEntity.ok().body(pokemonService.getAllPokemons());
    }

    @GetMapping("/getPokemonById")
    public ResponseEntity<PokemonDto> getPokemonById(@RequestParam(name = "pokemonId") long pokemonId){
        return ResponseEntity.ok().body(pokemonService.getPokemonById(pokemonId));
    }

    @GetMapping("/getPokemonByName")
    public ResponseEntity<PokemonDto> getPokemonByName(@RequestParam(name = "pokemonName") String pokemonName){
        return ResponseEntity.ok().body(pokemonService.getPokemonByName(pokemonName));
    }

    @PostMapping("/addPokemon")
    public ResponseEntity<String> addPokemon(@RequestBody @Valid Pokemon pokemon){
        return ResponseEntity.ok().body(pokemonService.addPokemon(pokemon));
    }

    @PostMapping("/deletePokemon")
    public ResponseEntity<String> deletePokemon(@RequestParam(name = "pokemonId") long pokemonId){
        return ResponseEntity.ok().body(pokemonService.deletePokemon(pokemonId));
    }
}


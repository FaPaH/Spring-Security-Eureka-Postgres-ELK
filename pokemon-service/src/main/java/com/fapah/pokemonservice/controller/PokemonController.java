package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.service.PokemonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
@RequiredArgsConstructor
@Slf4j
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/getAllPokemons")
    public ResponseEntity<List<PokemonDto>> getAllPokemons(){
        log.info("Get all Pokemons");
        return ResponseEntity.ok().body(pokemonService.getAllPokemons());
    }

    @GetMapping("/getPokemonById")
    public ResponseEntity<PokemonDto> getPokemonById(@RequestParam(name = "pokemonId") long pokemonId){
        log.info("Get Pokemon by id: {}", pokemonId);
        return ResponseEntity.ok().body(pokemonService.getPokemonById(pokemonId));
    }

    @GetMapping("/getPokemonByName")
    public ResponseEntity<PokemonDto> getPokemonByName(@RequestParam(name = "pokemonName") String pokemonName){
        log.info("Get Pokemon by name: {}", pokemonName);
        return ResponseEntity.ok().body(pokemonService.getPokemonByName(pokemonName));
    }

    @PostMapping("/addPokemon")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> addPokemon(@RequestBody @Valid PokemonDto pokemonDto){
        log.info("Add Pokemon: {}", pokemonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.addPokemon(pokemonDto));
    }

    @DeleteMapping("/deletePokemon")
    public ResponseEntity<String> deletePokemon(@RequestParam(name = "pokemonId") long pokemonId){
        log.info("Delete Pokemon: {}", pokemonId);
        pokemonService.deletePokemon(pokemonId);
        return ResponseEntity.ok().body("Pokemon deleted successfully");
    }
}


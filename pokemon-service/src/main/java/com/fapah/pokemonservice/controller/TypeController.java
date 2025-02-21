package com.fapah.pokemonservice.controller;

import com.fapah.pokemonservice.dto.PokemonDto;
import com.fapah.pokemonservice.dto.TypeDto;
import com.fapah.pokemonservice.entity.Pokemon;
import com.fapah.pokemonservice.entity.Type;
import com.fapah.pokemonservice.service.PokemonService;
import com.fapah.pokemonservice.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
@Slf4j
public class TypeController {

    private final TypeService typeService;

    @GetMapping("/getAllType")
    public ResponseEntity<List<TypeDto>> getAllTypes(){
        log.info("Get all types");
        return ResponseEntity.ok().body(typeService.getAllTypes());
    }

    @GetMapping("/getTypeById")
    public ResponseEntity<TypeDto> getTypeById(@RequestParam(name = "typeId") long typeId){
        log.info("Get type by id {}", typeId);
        return ResponseEntity.ok().body(typeService.getTypeById(typeId));
    }

    @GetMapping("/getTypeByName")
    public ResponseEntity<TypeDto> getTypeByName(@RequestParam(name = "typeName") String typeName){
        log.info("Get type by name {}", typeName);
        return ResponseEntity.ok().body(typeService.getTypeByName(typeName));
    }

    @PostMapping("/addType")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeDto> addType(@RequestBody @Valid TypeDto typeDto){
        log.info("Add type {}", typeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.addType(typeDto));
    }

    @DeleteMapping("/deleteType")
    public ResponseEntity<String> deleteType(@RequestParam(name = "typeId") long typeId){
        log.info("Delete type {}", typeId);
        typeService.deleteType(typeId);
        return ResponseEntity.ok().body("Type deleted successfully");
    }
}

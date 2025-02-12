package com.fapah.pokemonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PokemonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokemonServiceApplication.class, args);
    }

}

package com.infobasic.arcade.arcade_rank.controller;

import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.service.VideogameService;

import jakarta.validation.Valid;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videogames")
public class VideogameController {

    private final VideogameService videogameService;

    public VideogameController(VideogameService videogameService) {
        this.videogameService = videogameService;
    }


    //Add a videogame
    @PostMapping
    public ResponseEntity<?> saveVideogame(@Valid @RequestBody Videogame videogame, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Check if the videogame already exists
        if (videogameService.findByName(videogame.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Videogame already exists");
        }

        Videogame savedVideogame = videogameService.saveVideogame(videogame);
        return ResponseEntity.ok(savedVideogame);
    }


    //Get a videogame by name
    @GetMapping("/{name}")
    public ResponseEntity<Videogame> findByName(@PathVariable String name) {
        return videogameService.findByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}

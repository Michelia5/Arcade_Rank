package com.infobasic.arcade.arcade_rank.controller;

import java.util.List;
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

import com.infobasic.arcade.arcade_rank.model.Player;
import com.infobasic.arcade.arcade_rank.service.PlayerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    //Create a player
    @PostMapping
    public ResponseEntity<?> savePlayer(@Valid @RequestBody Player player, BindingResult result) {
        if (result.hasErrors()) {
        List<String> errors = result.getAllErrors().stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
        }

        // Check if the username already exists
        if (playerService.findPlayerByUsername(player.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        Player savedPlayer = playerService.savePlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }


    //Get a player by username
    @GetMapping("/{username}")
    public ResponseEntity<Player> findPlayerByUsername(@PathVariable String username) {
        return playerService.findPlayerByUsername(username).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    
}

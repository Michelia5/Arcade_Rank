package com.infobasic.arcade.arcade_rank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.infobasic.arcade.arcade_rank.model.Player;
import com.infobasic.arcade.arcade_rank.repository.PlayerRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    //Create a player
    public Player savePlayer(@Valid Player player) {
        if (playerRepository.findByUsername(player.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return playerRepository.save(player);
    }

    //Find a player by username
    public Optional<Player> findPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }


    //Get all players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }


    //Remove a player by Id
    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }

}

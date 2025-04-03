package com.infobasic.arcade.arcade_rank.controller_view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.infobasic.arcade.arcade_rank.model.Player;
import com.infobasic.arcade.arcade_rank.service.PlayerService;

@Controller
public class PlayerViewController {

    private final PlayerService playerService;

    public PlayerViewController(PlayerService playerService) {
        this.playerService = playerService;
    }

    
    //Show the List of all players
    @GetMapping("/players")
    public String showPlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "players";
    }

    // Show the form to add a new player
    @GetMapping("/add-player")
    public String showAddPlayerForm() {
        return "add-player";
    }

    // Add a new player
    @PostMapping("/add-player")
    public String addPlayer(@RequestParam("username") String username, Model model) {
        try {
            Player player = new Player(username);
            playerService.savePlayer(player);
            return "redirect:/players";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add-player";  // Return the page with an error message
        }
    }

    // Remove a player
    @PostMapping("/delete-player")
    public String deletePlayer(@RequestParam Long id) {
        playerService.deletePlayerById(id);
        return "redirect:/players";
    }
}


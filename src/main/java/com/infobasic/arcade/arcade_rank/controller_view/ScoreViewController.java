package com.infobasic.arcade.arcade_rank.controller_view;

import com.infobasic.arcade.arcade_rank.model.Player;
import com.infobasic.arcade.arcade_rank.model.Score;
import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.service.PlayerService;
import com.infobasic.arcade.arcade_rank.service.VideogameService;
import com.infobasic.arcade.arcade_rank.service.ScoreService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScoreViewController {

    private final PlayerService playerService;
    private final VideogameService videogameService;
    private final ScoreService scoreService;

    public ScoreViewController(PlayerService playerService, VideogameService videogameService, ScoreService scoreService) {
        this.playerService = playerService;
        this.videogameService = videogameService;
        this.scoreService = scoreService;
    }


    // Show the List of all scores
    @GetMapping("/scores")
    public String showScores(Model model) {
        List<Score> scores = scoreService.getAllScores();
        model.addAttribute("scores", scores);
        return "scores";
    }

    // Get the form to add a score
    @GetMapping("/add-score")
    public String showAddScoreForm(Model model) {
        List<Player> players = playerService.getAllPlayers(); 
        List<Videogame> videogames = videogameService.getAllVideogames();
        model.addAttribute("players", players);
        model.addAttribute("videogames", videogames);
        return "add-score"; // Thymeleaf template for adding a score
    }

    // Add a score
    @PostMapping("/add-score")
    public String addScore(@RequestParam Long playerId, @RequestParam Long videogameId, @RequestParam int score, Model model) {
        try {
            scoreService.insertOrUpdateScore(playerId, videogameId, score); // Insert or update the score
            return "redirect:/rankings/" + videogameId; // Redirect to the ranking page after
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add-score";  // Return the page with an error message
        }
    }
}

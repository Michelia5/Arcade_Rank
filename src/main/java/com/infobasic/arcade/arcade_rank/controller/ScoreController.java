package com.infobasic.arcade.arcade_rank.controller;

import com.infobasic.arcade.arcade_rank.service.ScoreService;
import com.infobasic.arcade.arcade_rank.model.Score;
import com.infobasic.arcade.arcade_rank.dto.ScoreDTO;
import com.infobasic.arcade.arcade_rank.model.Player;
import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.repository.PlayerRepository;
import com.infobasic.arcade.arcade_rank.repository.VideogameRepository;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;
    private final PlayerRepository playerRepository;
    private final VideogameRepository videogameRepository;

    public ScoreController(ScoreService scoreService, PlayerRepository playerRepository, VideogameRepository videogameRepository) {
        this.scoreService = scoreService;
        this.playerRepository = playerRepository;
        this.videogameRepository = videogameRepository;
    }

    // Add or update a score
    @PostMapping
    public ResponseEntity<?> saveScore(@Valid @RequestBody ScoreDTO scoreRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Obtain the player and the videogame from the db
        Player player = playerRepository.findById(scoreRequest.getPlayerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));
        Videogame videogame = videogameRepository.findById(scoreRequest.getVideogameId())
                .orElseThrow(() -> new RuntimeException("Videogame not found"));

        // Create the score
        Score score = new Score();
        score.setPlayer(player);
        score.setVideogame(videogame);
        score.setScore(scoreRequest.getScore());

        Score savedScore = scoreService.saveScore(score);
        return ResponseEntity.ok(savedScore);
    }

    // Get the top 5 scores of a videogame
    @GetMapping("/top5/{gameId}")
    public ResponseEntity<List<Map<String, Object>>> getTop5ScoresForVideogame(@PathVariable Long gameId) {
        List<Map<String, Object>> top5Scores = scoreService.getTop5ScoresForVideogame(gameId);
        return ResponseEntity.ok(top5Scores);
    }


    // Get the global ranking (top 5 players overall)
    @GetMapping("/top5")
    public ResponseEntity<List<Map<String, Object>>> getGlobalRanking() {
        return ResponseEntity.ok(scoreService.getGlobalRanking());
    }
}

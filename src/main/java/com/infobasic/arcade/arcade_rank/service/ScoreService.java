package com.infobasic.arcade.arcade_rank.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import java.util.stream.Collectors;

import com.infobasic.arcade.arcade_rank.model.Player;
import com.infobasic.arcade.arcade_rank.model.Score;
import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.repository.ScoreRepository;
import com.infobasic.arcade.arcade_rank.repository.PlayerRepository;
import com.infobasic.arcade.arcade_rank.repository.VideogameRepository;

@Service
@Validated
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final PlayerRepository playerRepository;
    private final VideogameRepository videogameRepository;

    public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository, VideogameRepository videogameRepository) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
        this.videogameRepository = videogameRepository;
    }


    //Add a score
    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    //Get all scores
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }


    // Find the top 5 scores for a videogame
    public List<Map<String, Object>> getTop5ScoresForVideogame(Long videogameId) {
        List<Score> scores = scoreRepository.findByVideogameId(videogameId);
    
        List<Map<String, Object>> topScores = new ArrayList<>();
    
        // Group by players and sum the scores
        Map<Long, Integer> playerScores = new HashMap<>();
        for (Score score : scores) {
            playerScores.put(score.getPlayer().getId(), playerScores.getOrDefault(score.getPlayer().getId(), 0) + score.getScore());
        }
    
        // Add global scores to the List
        for (Map.Entry<Long, Integer> entry : playerScores.entrySet()) {
            Player player = playerRepository.findById(entry.getKey()).orElse(null);
            if (player != null) {
                Map<String, Object> playerScore = new HashMap<>();
                playerScore.put("username", player.getUsername());
                playerScore.put("totalScore", entry.getValue());
                topScores.add(playerScore);
            }
        }
    
        // Order by total scores
        return topScores.stream()
                .sorted((p1, p2) -> Integer.compare((int) p2.get("totalScore"), (int) p1.get("totalScore")))
                .limit(5) // Limit to the top 5 scores
                .collect(Collectors.toList());
    }
    

    // Find the global ranking (top 5 players overall)
    public List<Map<String, Object>> getGlobalRanking() {
        List<Player> players = playerRepository.findAll();
        List<Map<String, Object>> globalRanking = new ArrayList<>();


        // Calculate total score for each player
        for (Player player : players) {
            int totalScore = 0;

            // For each videogame, get the player's score and multiply by difficulty
            List<Score> scores = scoreRepository.findByPlayerId(player.getId());
            for (Score score : scores) {
                Optional<Videogame> videogame = videogameRepository.findById(score.getVideogame().getId());
                if (videogame.isPresent()) {
                    totalScore += score.getScore() * videogame.get().getDifficultyCoefficient();
                }
            }

            Map<String, Object> playerRanking = new HashMap<>();
            playerRanking.put("username", player.getUsername());
            playerRanking.put("totalScore", totalScore);
            globalRanking.add(playerRanking);
        }

        // Sorting the players by total score
        return globalRanking.stream()
                .sorted((p1, p2) -> Integer.compare((int) p2.get("totalScore"), (int) p1.get("totalScore")))
                .limit(5) // Limit to top 5 players
                .collect(Collectors.toList());
    }



    // Insert a game (create or update a score)
    public Score insertOrUpdateScore(Long playerId, Long videogameId, int score) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        Optional<Videogame> videogameOptional = videogameRepository.findById(videogameId);

        if (playerOptional.isEmpty() || videogameOptional.isEmpty()) {
            throw new RuntimeException("Player or Videogame not found");
        }

        Player player = playerOptional.get();
        Videogame videogame = videogameOptional.get();

        // Moltiply the score by the difficulty coefficient
        int adjustedScore = (int) (score * videogame.getDifficultyCoefficient());

        // Check if the player or the game already have a score
        Score existingScore = scoreRepository.findByPlayerIdAndVideogameId(playerId, videogameId);
    
        if (existingScore != null) {
            existingScore.setScore(adjustedScore);  // Update the score
            return scoreRepository.save(existingScore);
        } else {
            // If the score does not exist, create a new one
            Score newScore = new Score();
            newScore.setPlayer(player);
            newScore.setVideogame(videogame);
            newScore.setScore(score);
            return scoreRepository.save(newScore);
        }
    }

    // Delete a score
    public void deleteScore(Long scoreId) {
        scoreRepository.deleteById(scoreId);
    }
    
}

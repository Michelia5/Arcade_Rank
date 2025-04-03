package com.infobasic.arcade.arcade_rank.repository;

import com.infobasic.arcade.arcade_rank.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    // Find all scores of a player
    List<Score> findByPlayerId(Long playerId);

    // Find all the scores of a videogame
    List<Score> findByVideogameId(Long videogameId);

    // Find the top scores of a videogame
    @Query("SELECT s FROM Score s WHERE s.videogame.id = :videogameId ORDER BY s.score DESC")
    List<Score> findTopScoresByVideogame(Long videogameId);

    // Find the score of a player in a videogame
    Score findByPlayerIdAndVideogameId(Long playerId, Long videogameId);

    // Find the top 5 scores (ordered by descending score)
    @Query("SELECT s FROM Score s ORDER BY s.score DESC")
    List<Score> findTop5ByOrderByScoreDesc();
}

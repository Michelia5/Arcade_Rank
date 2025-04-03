package com.infobasic.arcade.arcade_rank.controller_view;

import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.service.ScoreService;
import com.infobasic.arcade.arcade_rank.service.VideogameService;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RankingViewController {

    private final VideogameService videogameService;

    private final ScoreService scoreService;

    public RankingViewController(ScoreService scoreService, VideogameService videogameService) {
        this.scoreService = scoreService;
        this.videogameService = videogameService;
    }


    // Get the top scores for a videogame
    @GetMapping("/rankings/{videogameId}")
    public String showRankingForVideogame(@PathVariable Long videogameId, Model model) {
        List<Map<String, Object>> ranking = scoreService.getTop5ScoresForVideogame(videogameId); // Game ranking
        Videogame videogame = videogameService.getVideogameById(videogameId);

        model.addAttribute("ranking", ranking);
        model.addAttribute("videogame", videogame);

        if (ranking.isEmpty()) {
            model.addAttribute("noScoresMessage", "No scores yet for this game.");
        }

        return "ranking-details";
    }

}


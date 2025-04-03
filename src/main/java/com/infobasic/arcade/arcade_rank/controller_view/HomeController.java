package com.infobasic.arcade.arcade_rank.controller_view;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.infobasic.arcade.arcade_rank.service.ScoreService;


@Controller
public class HomeController {

    private final ScoreService scoreService;

    public HomeController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Get the top 5 score from the db
        List<Map<String, Object>> globalRanking = scoreService.getGlobalRanking();
        model.addAttribute("globalRanking", globalRanking);

        return "home";  // Name of the thymeleaf template   
    }
}


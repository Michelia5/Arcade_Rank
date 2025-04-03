package com.infobasic.arcade.arcade_rank.controller_view;

import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.service.VideogameService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideogameViewController {

    private final VideogameService videogameService;

    public VideogameViewController(VideogameService videogameService) {
        this.videogameService = videogameService;
    }


    // Show the List of all videogames
    @GetMapping("/videogames")
    public String showVideogames(Model model) {
        List<Videogame> videogames = videogameService.getAllVideogames();
        model.addAttribute("videogames", videogames);
        return "videogames";
    }

    // Get the form to add a new videogame
    @GetMapping("/add-videogame")
    public String showAddVideogameForm() {
        return "add-videogame"; // Thymeleaf template for adding a videogame
    }

    // Add a new videogame
    @PostMapping("/add-videogame")
    public String addVideogame(@RequestParam String name, @RequestParam int difficultyCoefficient, Model model) {
        try {
            Videogame videogame = new Videogame(name, difficultyCoefficient); // Create videogame
            videogameService.saveVideogame(videogame); // Save videogame to the database
            return "redirect:/videogames"; // Redirect to the home page after creating the videogame
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add-videogame";  // Return the page with an error message
        }
    }

    // Delete a videogame
    @PostMapping("/delete-videogame")
    public String deleteVideogame(@RequestParam Long id) {
        videogameService.deleteVideogameById(id);
        return "redirect:/videogames";
    }

}


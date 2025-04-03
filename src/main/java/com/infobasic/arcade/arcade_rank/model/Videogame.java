package com.infobasic.arcade.arcade_rank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Videogame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;

    @Min(1) 
    @Max(5)
    private int difficultyCoefficient;

    // Add a contructor to create a Videogame
    public Videogame(String name, int difficultyCoefficient) {
        this.name = name;
        this.difficultyCoefficient = difficultyCoefficient;
    }
}

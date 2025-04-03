package com.infobasic.arcade.arcade_rank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    private Long playerId;
    private Long videogameId;
    private int score;
}

package com.german.dungeons.and.dragons.model;

import lombok.Data;

@Data
public class ResultOfSolvingTask {
    private Boolean success;
    private Integer lives;
    private Integer gold;
    private Integer score;
    private Integer highScore;
    private Integer turn;
    private String message;
}

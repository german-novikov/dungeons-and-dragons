package com.german.dungeons.and.dragons.model;

import lombok.Data;

@Data
public class GameStartResponse {
    private String gameId;
    private Integer lives;
    private Integer gold;
    private Integer level;
    private Integer score;
    private Integer highScore;
    private Integer turn;
}

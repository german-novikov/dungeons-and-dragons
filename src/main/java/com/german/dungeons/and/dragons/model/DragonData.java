package com.german.dungeons.and.dragons.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DragonData {
    private String name;
    private Integer level;
    private Integer gold;
    private Integer lives;
    private Integer score;
    private String gameId;
    private Integer failedTask;

    @Override
    public String toString(){
        return String.format("Dragon name: %s, level %s, gold %s, lives %s", name, level, gold, lives);
    }
}

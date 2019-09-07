package com.german.dungeons.and.dragons.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.german.dungeons.and.dragons.enums.TaskProbability;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private String adId;
    private String message;
    private Integer reward;
    private Integer expiresIn;
    private TaskProbability probability;
    private Integer encrypted = 0;

    public void setProbability(String probability){
        this.probability = TaskProbability.getByTitle(probability);
    }
}

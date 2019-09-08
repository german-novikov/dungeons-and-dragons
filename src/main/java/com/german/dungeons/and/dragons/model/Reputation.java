package com.german.dungeons.and.dragons.model;

import lombok.Data;

@Data
public class Reputation {
    private Double people;
    private Double state;
    private Double underworld;

    @Override
    public String toString(){
        return String.format("Reputation people %s, state %s, underworld %s", people, state, underworld);
    }
}

package com.german.dungeons.and.dragons.enums;

import com.german.dungeons.and.dragons.services.DragonService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public enum TaskProbability {
    PIECE_OF_CAKE("Piece of cake", 0),
    SUICIDE_MISSION("Suicide mission", 9),
    RATHER_DETRIMENTAL("Rather detrimental", 4),
    WALK_IN_THE_PARK("Walk in the park", 1),
    GAMBLE("Gamble", 5),
    SURE_THING("Sure thing",  2),
    HMMM("Hmmm....", 7),
    QUITE_LIKELY("Quite likely", 3),
    RISKY("Risky", 6),
    PLAYING_WITH_FIRE("Playing with fire", 8),
    ENCRYPTED("Encrypted", 100);

    private final String title;
    private final Integer level;

    TaskProbability(String title, Integer level){
        this.title = title;
        this.level = level;
    }
    private static Logger LOG = LoggerFactory.getLogger(DragonService.class);

    public static TaskProbability getByTitle(String title) {
        try{
            for (TaskProbability taskProbability : TaskProbability.values()) {
                if (taskProbability.getTitle().equalsIgnoreCase(title)) return taskProbability;
            }
        }catch (RuntimeException ex){
            LOG.error("Unsupported task probability " + title);
            return ENCRYPTED;
        }
        return ENCRYPTED;
    }
}

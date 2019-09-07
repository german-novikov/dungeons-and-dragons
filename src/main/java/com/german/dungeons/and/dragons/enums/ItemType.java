package com.german.dungeons.and.dragons.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    WINGPOTMAX("wingpotmax"),
    HEALING_POTION("hpot"),
    BOOK_OF_MEGATRICKS("mtrix"),
    BOOK_OF_TRICKS("tricks"),
    CLAW_HONING("ch"),
    CLAW_SHARPENING("cs"),
    POTION_OF_AWESOME_WINGS("wingpotmax"),
    POTION_OF_STRONGER_WINGS("wingpot");

    private String value;

    ItemType(String value) {
        this.value = value;
    }
}

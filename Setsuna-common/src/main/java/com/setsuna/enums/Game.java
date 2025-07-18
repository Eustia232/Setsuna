package com.setsuna.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Game {
    SENRENBANKA("千恋万花"), MINDEADBLOOD("献给支配者的狂死曲");

    private final String value;

    Game(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Game fromValue(String value) {
        for (Game g : Game.values()) {
            if (g.value.equals(value)) {
                return g;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found in inventory");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

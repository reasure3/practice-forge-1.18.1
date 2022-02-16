package com.reasure.practice.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum StandingDirection implements StringRepresentable {
    UP("up"),
    NORMAL("normal"),
    DOWN("down");

    private final String name;

    StandingDirection(String name) {
        this.name = name;
    }

    @NotNull
    public String getSerializedName() {
        return this.name;
    }
}

package net.vashal.tistheseason.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ToyRobotVariant {
    DEFAULT(0),
    WOOD(1),
    EVA(2),
    BEE(3),
    DEFAULT_INVERTED(4),
    WOOD_INVERTED(5),
    BEE_INVERTED(6);

    private static final ToyRobotVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ToyRobotVariant::getId)).toArray(ToyRobotVariant[]::new);
    private final int id;

    ToyRobotVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ToyRobotVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

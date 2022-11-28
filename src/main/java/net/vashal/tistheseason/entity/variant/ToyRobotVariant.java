package net.vashal.tistheseason.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ToyRobotVariant {
    BLACK(0),
    WHITE(1),
    ORANGE(2),
    MAGENTA(3),
    LIGHT_BLUE(4),
    YELLOW(5),
    LIME(6),
    PINK(7),
    GRAY(8),
    LIGHT_GRAY(9),
    CYAN(10),
    PURPLE(11),
    BLUE(12),
    BROWN(13),
    GREEN(14),
    RED(15);


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

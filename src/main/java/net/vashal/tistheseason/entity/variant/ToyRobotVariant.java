package net.vashal.tistheseason.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ToyRobotVariant {
    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHT_BLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    GRAY(7),
    LIGHT_GRAY(8),
    CYAN(9),
    PURPLE(10),
    BLUE(11),
    BROWN(12),
    GREEN(13),
    RED(14),
    BLACK(15);


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

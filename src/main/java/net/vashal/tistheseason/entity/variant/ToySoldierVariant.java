package net.vashal.tistheseason.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ToySoldierVariant {
    DEFAULT(0),
    ALTERNATE(1);

    private static final ToySoldierVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ToySoldierVariant::getId)).toArray(ToySoldierVariant[]::new);
    private final int id;

    ToySoldierVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ToySoldierVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

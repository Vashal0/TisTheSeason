package net.vashal.tistheseason.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ToyTankVariant {
    DEFAULT(0),
    SAND(1);

    private static final ToyTankVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ToyTankVariant::getId)).toArray(ToyTankVariant[]::new);
    private final int id;

    ToyTankVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ToyTankVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

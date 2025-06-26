package com.boreal.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Stats principales (1-100). Arrancan en 25 con 50 puntos libres para repartir.
 */
public final class PrimaryStats implements Serializable {

    /* ───── ENUM ───── */
    public enum Stat {
        STRENGTH("STRENGTH"), AGILITY("AGILITY"), ENDURANCE("ENDURANCE"), INTELLIGENCE("INTELLIGENCE"), PERCEPTION("PERCEPTION"), CHARISMA("CHARISMA"), WILLPOWER("WILLPOWER"), LUCK("LUCK");

        private final String label;

        Stat(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    /* ───── CONFIG ───── */
    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int INITIAL_VALUE = 25;
    private static final int INITIAL_POINTS = 50;

    private final EnumMap<Stat, Integer> values = new EnumMap<>(Stat.class);
    private int remainingPoints = INITIAL_POINTS;

    /* ───── CONSTRUCTOR ───── */
    public PrimaryStats() {
        reset();
    }

    /* ───── GETTERS ───── */
    public int get(Stat s) {
        return values.get(s);
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    public Map<Stat, Integer> asMap() {
        return Map.copyOf(values);
    }

    /* ───── ACCIONES ───── */

    /**
     * Restaura todos los valores y puntos libres al estado inicial.
     */
    public void reset() {
        for (Stat s : Stat.values()) values.put(s, INITIAL_VALUE);
        remainingPoints = INITIAL_POINTS;
    }

    /**
     * Sube +1 la stat si hay puntos suficientes.
     */
    public boolean raise(Stat s) {
        int cost = costToRaise(values.get(s));
        if (cost > remainingPoints || values.get(s) >= MAX) return false;
        values.put(s, values.get(s) + 1);
        remainingPoints -= cost;
        return true;
    }

    /**
     * Baja –1 la stat (nunca por debajo del valor inicial) y devuelve puntos.
     */
    public boolean lower(Stat s) {
        int current = values.get(s);
        if (current <= INITIAL_VALUE) return false;
        int refund = costToRaise(current - 1);   // lo que costaría volverlo a subir
        values.put(s, current - 1);
        remainingPoints += refund;
        return true;
    }

    /* ───── COSTE ESCALONADO ───── */
    private static int costToRaise(int fromValue) {
        if (fromValue >= 99) return Integer.MAX_VALUE;
        if (fromValue < 40) return 1;
        if (fromValue < 70) return 2;
        if (fromValue < 90) return 4;
        return 6; // 90-99
    }

    /* ───── DEBUG ───── */
    @Override
    public String toString() {
        return values + " | pts=" + remainingPoints;
    }
}

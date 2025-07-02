package com.boreal.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import static com.boreal.model.PrimaryStats.Stat.*;

/**
 * Atributos secundarios derivados de las stats principales.
 * Todos los valores se recalculan, por lo que no se serializan.
 */
public final class DerivedAttributes implements Serializable {

    /* Si necesitas más, añade aquí y en las pantallas */
    public enum Attr {
        MAX_HEALTH, STAMINA, MOVE_SPEED, ATTACK_SPEED, MELEE_DAMAGE, RANGED_ACCURACY, EVASION, CARRY_WEIGHT, DAMAGE_RESIST, INFECTION_RESIST, CRAFT_SPEED, XP_GAIN, TRADE_DISCOUNT, MORALE_BONUS, COOLDOWN_REDUCTION, LOOT_CHANCE, CRIT_CHANCE
    }

    private final EnumMap<Attr, Float> values = new EnumMap<>(Attr.class);

    public DerivedAttributes() {
        for (Attr a : Attr.values()) values.put(a, 0f);
    }

    /*───────────────────── FÓRMULAS ─────────────────────*/
    public void recalc(PrimaryStats ps) {

        int STR = ps.get(STRENGTH);
        int AGI = ps.get(AGILITY);
        int END = ps.get(ENDURANCE);
        int INT = ps.get(INTELLIGENCE);
        int PER = ps.get(PERCEPTION);
        int CHA = ps.get(CHARISMA);
        int WIL = ps.get(WILLPOWER);
        int LCK = ps.get(LUCK);

        /* ❶ Supervivencia --------------------------------------------------*/
        values.put(Attr.MAX_HEALTH, 50f + 6f * END);
        values.put(Attr.STAMINA, 30f + 4f * END + 0.5f * STR);      // residuo STR
        values.put(Attr.DAMAGE_RESIST, 5f + 0.25f * END + 0.10f * WIL);   // residuo WIL
        values.put(Attr.INFECTION_RESIST, 5f + 0.30f * END + 0.20f * WIL);   // residuo WIL

        /* ❷ Combate --------------------------------------------------------*/
        values.put(Attr.MELEE_DAMAGE, 1f + 0.15f * STR);
        values.put(Attr.RANGED_ACCURACY, 50f + 0.60f * PER);
        values.put(Attr.ATTACK_SPEED, 1f + 0.030f * AGI);
        values.put(Attr.EVASION, 5f + 0.20f * AGI + 0.10f * PER + 0.10f * LCK); // residuo LCK
        values.put(Attr.CRIT_CHANCE, 0f + 0.35f * LCK + 0.10f * PER);               // PER residual

        /* ❸ Movilidad / logística -----------------------------------------*/
        values.put(Attr.MOVE_SPEED, 2.5f + 0.045f * AGI);
        values.put(Attr.CARRY_WEIGHT, 10f + 0.90f * STR);

        /* ❹ Producción / progreso -----------------------------------------*/
        values.put(Attr.CRAFT_SPEED, 1f + 0.035f * INT);
        values.put(Attr.XP_GAIN, 1f + 0.030f * INT);

        /* ❺ Social / gestión ----------------------------------------------*/
        values.put(Attr.TRADE_DISCOUNT, 0f + 0.50f * CHA);
        values.put(Attr.MORALE_BONUS, 0f + 0.40f * CHA);

        /* ❻ Habilidades especiales ----------------------------------------*/
        values.put(Attr.COOLDOWN_REDUCTION, 0f + 0.50f * WIL);

        /* ❼ Suerte ---------------------------------------------------------*/
        values.put(Attr.LOOT_CHANCE, 0f + 0.60f * LCK);
    }

    /*───────────────────── Getters ─────────────────────*/
    public float get(Attr a) {
        return values.get(a);
    }

    public Map<Attr, Float> asMap() {
        return Map.copyOf(values);
    }
}

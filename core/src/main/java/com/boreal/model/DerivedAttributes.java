package com.boreal.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import static com.boreal.model.PrimaryStats.Stat.*;

public final class DerivedAttributes implements Serializable {

    public enum Attr {
        MAX_HEALTH, STAMINA, MOVE_SPEED, ATTACK_SPEED, MELEE_DAMAGE, RANGED_ACCURACY, EVASION, CARRY_WEIGHT, DAMAGE_RESIST, INFECTION_RESIST, CRAFT_SPEED, XP_GAIN, TRADE_DISCOUNT, MORALE_BONUS, COOLDOWN_REDUCTION, LOOT_CHANCE, CRIT_CHANCE
    }

    private final EnumMap<Attr, Float> values = new EnumMap<>(Attr.class);

    public DerivedAttributes() {
        for (Attr a : Attr.values()) values.put(a, 0f);
    }

    /**
     * Re-calcula todos los atributos con progresión más lenta.
     */
    public void recalc(PrimaryStats p) {

        float STR = p.get(STRENGTH);
        float AGI = p.get(AGILITY);
        float END = p.get(ENDURANCE);
        float INT = p.get(INTELLIGENCE);
        float PER = p.get(PERCEPTION);
        float CHA = p.get(CHARISMA);
        float WIL = p.get(WILLPOWER);
        float LCK = p.get(LUCK);

        /* ❶ Supervivencia */
        values.put(Attr.MAX_HEALTH, 50f + 3f * END);          // ↓ de 6 → 3
        values.put(Attr.STAMINA, 30f + 2f * END + 0.25f * STR);
        values.put(Attr.DAMAGE_RESIST, 0.15f * END + 0.06f * WIL); // ↓
        values.put(Attr.INFECTION_RESIST, 0.06f * END + 0.12f * WIL); // ↓

        /* ❷ Combate */
        values.put(Attr.MELEE_DAMAGE, 1f + 0.08f * STR);         // ↓ 0.15 → 0.08
        values.put(Attr.RANGED_ACCURACY, 50f + 0.40f * PER);        // ↓
        values.put(Attr.ATTACK_SPEED, 1f + 0.018f * AGI);         // ↓ 0.03 → 0.018
        values.put(Attr.EVASION, 5f + 0.12f * AGI + 0.05f * PER + 0.06f * LCK);
        values.put(Attr.CRIT_CHANCE, 0.22f * LCK + 0.10f * PER + 0.08f * AGI); // ↓

        /* ❸ Movilidad & logística */
        values.put(Attr.MOVE_SPEED, 2.5f + 0.03f * AGI);       // ↓
        values.put(Attr.CARRY_WEIGHT, 10f + 0.50f * STR);        // ↓

        /* ❹ Producción / progreso */
        values.put(Attr.CRAFT_SPEED, 1f + 0.020f * INT);         // ↓
        values.put(Attr.XP_GAIN, 1f + 0.018f * INT);         // ↓

        /* ❺ Social */
        values.put(Attr.TRADE_DISCOUNT, 0.30f * CHA);              // ↓
        values.put(Attr.MORALE_BONUS, 0.25f * CHA);              // ↓
        values.put(Attr.LOOT_CHANCE, 0.40f * LCK + 0.08f * CHA); // ↓

        /* ❻ Cooldowns */
        values.put(Attr.COOLDOWN_REDUCTION, 0.35f * WIL + 0.18f * INT);                       // ↓
    }

    public float get(Attr a) {
        return values.get(a);
    }

    public Map<Attr, Float> asMap() {
        return Map.copyOf(values);
    }
}

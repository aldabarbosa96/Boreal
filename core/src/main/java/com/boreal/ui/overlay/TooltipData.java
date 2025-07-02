package com.boreal.ui.overlay;

import com.boreal.model.DerivedAttributes;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.model.Skills.Skill;
import com.boreal.model.DerivedSkills;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TooltipData {

    private TooltipData() {/* no instancias */}

    /**
     * Descripción de cada stat.
     */
    private static final Map<PrimaryStats.Stat, String> STAT_DESCRIPTIONS = Map.ofEntries(Map.entry(PrimaryStats.Stat.STRENGTH, "Aumenta el daño cuerpo a cuerpo\nAumenta el peso que puedes cargar."), Map.entry(PrimaryStats.Stat.AGILITY, "Mejora la velocidad de ataque\nAumenta la probabilidad de evasión."), Map.entry(PrimaryStats.Stat.ENDURANCE, "Incrementa la salud máxima\nMejora la resistencia al daño."), Map.entry(PrimaryStats.Stat.INTELLIGENCE, "Aumenta la experiencia ganada\nEficacia en habilidades técnicas."), Map.entry(PrimaryStats.Stat.PERCEPTION, "Mejora la puntería y precisión\nAumenta la detección de peligros."), Map.entry(PrimaryStats.Stat.CHARISMA, "Mejora las opciones de compra/venta\nAumenta las opciones de diálogo."), Map.entry(PrimaryStats.Stat.WILLPOWER, "Reduce el coste de habilidades especiales\ny resistencia al estrés."), Map.entry(PrimaryStats.Stat.LUCK, "Aumenta la probabilidad de críticos\ny hallazgos raros."));

    public static String statTooltip(PrimaryStats.Stat s, int val) {

        // helper para formatear: "x3.2 (+0.15)" o "75 % (+0.6 %)"
        class F {
            String mult(float v, float d) {
                return String.format("x%.2f (+%.2f)", v, d);
            }

            String flat(float v, float d) {
                return String.format("%.0f (+%.0f)", v, d);
            }

            String pct(float v, float d) {
                return String.format("%.1f %% (+%.1f %%)", v, d);
            }
        }
        F f = new F();

        StringBuilder body = new StringBuilder();

        switch (s) {

            /*──────── STRENGTH ────────*/
            case STRENGTH: {
                body.append("Strength: boosts melee and carry power.\n\n");

                float melee = 1f + 0.15f * val;
                float carry = 10f + 0.90f * val;
                float stam = 30f + 4f * 0 + 0.5f * val;  // 0*END contrib simplificado

                body.append("- Melee damage ").append(f.mult(melee, 0.15f)).append("\n");
                body.append("- Carry weight ").append(f.flat(carry, 0.90f)).append(" kg\n");
                body.append("- Stamina      ").append(f.flat(stam, 0.5f));
                break;
            }

            /*──────── AGILITY ─────────*/
            case AGILITY: {
                body.append("Agility: speed, cadence and evasion.\n\n");

                float move = 2.5f + 0.045f * val;
                float atkSpd = 1f + 0.03f * val;
                float evade = 5f + 0.20f * val + 0.10f * 0;  // +PER part =0 para delta

                body.append("- Move speed   ").append(f.flat(move, 0.045f)).append(" m/s\n");
                body.append("- Attack speed ").append(f.mult(atkSpd, 0.03f)).append("\n");
                body.append("- Evasion      ").append(f.pct(evade, 0.20f));
                break;
            }

            /*──────── ENDURANCE ───────*/
            case ENDURANCE: {
                body.append("Endurance: raw toughness and resistances.\n\n");

                float hp = 50f + 6f * val;
                float stam = 30f + 4f * val + 0.5f * 0;
                float dr = 0.25f * val;                 // %
                float inf = 0.10f * val;                 // %

                body.append("- Max health   ").append(f.flat(hp, 6f)).append("\n");
                body.append("- Stamina      ").append(f.flat(stam, 4f)).append("\n");
                body.append("- Damage resist").append(f.pct(dr, 0.25f)).append("\n");
                body.append("- Infection res").append(f.pct(inf, 0.10f));
                break;
            }

            /*──────── INTELLIGENCE ────*/
            case INTELLIGENCE: {
                body.append("Intelligence: crafting speed & experience.\n\n");

                float craft = 1f + 0.035f * val;
                float xp = 1f + 0.030f * val;
                float cdRed = 0.30f * 0 + 0.30f * val;    // solo INT parte aquí

                body.append("- Craft speed    ").append(f.mult(craft, 0.035f)).append("\n");
                body.append("- XP gain        ").append(f.mult(xp, 0.030f)).append("\n");
                body.append("- Cooldown red.  ").append(f.pct(cdRed, 0.30f));
                break;
            }

            /*──────── PERCEPTION ──────*/
            case PERCEPTION: {
                body.append("Perception: ranged precision & awareness.\n\n");

                float acc = 50f + 0.60f * val;
                float crit = 0.35f * 0 + 0.15f * val;
                float evade = 5f + 0.20f * 0 + 0.10f * val + 0.10f * 0;

                body.append("- Accuracy   ").append(f.pct(acc, 0.60f)).append("\n");
                body.append("- Crit chance").append(f.pct(crit, 0.15f)).append("\n");
                body.append("- Evasion    ").append(f.pct(evade, 0.10f));
                break;
            }

            /*──────── CHARISMA ────────*/
            case CHARISMA: {
                body.append("Charisma: trade deals & morale.\n\n");

                float disc = 0.50f * val;
                float morale = 0.40f * val;
                float loot = 0.60f * 0 + 0.10f * val;

                body.append("- Trade discount ").append(f.pct(disc, 0.50f)).append("\n");
                body.append("- Morale bonus   ").append(f.flat(morale, 0.40f)).append("\n");
                body.append("- Loot chance    ").append(f.pct(loot, 0.10f));
                break;
            }

            /*──────── WILLPOWER ───────*/
            case WILLPOWER: {
                body.append("Willpower: cool mind & extra resistance.\n\n");

                float panic = 0.60f * val;
                float cdRed = 0.50f * val + 0.30f * 0;
                float inf = 0.10f * 0 + 0.20f * val;

                body.append("- Panic resist    ").append(f.pct(panic, 0.60f)).append("\n");
                body.append("- Cooldown red.   ").append(f.pct(cdRed, 0.50f)).append("\n");
                body.append("- Infection resist").append(f.pct(inf, 0.20f));
                break;
            }

            /*──────── LUCK ────────────*/
            case LUCK: {
                body.append("Luck: rare loot, crits and a dash of evasion.\n\n");

                float loot = 0.60f * val + 0.10f * 0;
                float crit = 0.35f * val + 0.15f * 0 + 0.10f * 0;
                float evade = 5f + 0.20f * 0 + 0.10f * 0 + 0.10f * val;

                body.append("- Loot chance ").append(f.pct(loot, 0.60f)).append("\n");
                body.append("- Crit chance ").append(f.pct(crit, 0.35f)).append("\n");
                body.append("- Evasion     ").append(f.pct(evade, 0.10f));
                break;
            }
        }

        return String.format("%s: %d%n%n%s", s.label(), val, body.toString());
    }


    /**
     * Genera el texto completo para un tooltip de profesión, con skills + bonuses.
     */
    public static String professionTooltip(Professions.Type p) {
        // 1) Habilidades derivadas
        List<Skill> skills = DerivedSkills.getDerivedSkills(p);
        String skillsPart = skills.stream().map(sk -> " - " + sk.label()).collect(Collectors.joining("\n"));
        // 2) Bonuses de stats
        EnumMap<PrimaryStats.Stat, Integer> mods = Professions.getModifiersFor(p);
        String bonusPart = mods.entrySet().stream().map(e -> String.format("+%d %s", e.getValue(), e.getKey().label())).collect(Collectors.joining("\n"));

        StringBuilder sb = new StringBuilder(p.label()).append(" Skills:\n").append(skillsPart);
        if (!mods.isEmpty()) {
            sb.append("\n\nStat Bonuses:\n").append(bonusPart);
        }
        return sb.toString();
    }

    private static final Map<DerivedAttributes.Attr,String> ATTR_DESCS =
        java.util.Map.ofEntries(
            Map.entry(DerivedAttributes.Attr.MAX_HEALTH,      "Total health points you can lose before dying."),
            Map.entry(DerivedAttributes.Attr.STAMINA,         "Energy used for sprinting and actions; refills over time."),
            Map.entry(DerivedAttributes.Attr.MOVE_SPEED,      "How fast you walk or run."),
            Map.entry(DerivedAttributes.Attr.ATTACK_SPEED,    "Time between successive attacks."),
            Map.entry(DerivedAttributes.Attr.MELEE_DAMAGE,    "Multiplier applied to close-combat hits."),
            Map.entry(DerivedAttributes.Attr.RANGED_ACCURACY, "Chance your shots hit the target."),
            Map.entry(DerivedAttributes.Attr.EVASION,         "Probability to dodge enemy attacks or detection."),
            Map.entry(DerivedAttributes.Attr.CARRY_WEIGHT,    "Maximum weight (kg) you can carry without penalties."),
            Map.entry(DerivedAttributes.Attr.DAMAGE_RESIST,   "Percentage of incoming damage absorbed."),
            Map.entry(DerivedAttributes.Attr.INFECTION_RESIST,"Chance to avoid infection after a bite or scratch."),
            Map.entry(DerivedAttributes.Attr.CRAFT_SPEED,     "Multiplier for crafting and building tasks."),
            Map.entry(DerivedAttributes.Attr.XP_GAIN,         "Extra experience earned from any action."),
            Map.entry(DerivedAttributes.Attr.TRADE_DISCOUNT,  "Percentage you save when buying (and lose when selling)."),
            Map.entry(DerivedAttributes.Attr.MORALE_BONUS,    "Flat boost to the morale of followers in your group."),
            Map.entry(DerivedAttributes.Attr.COOLDOWN_REDUCTION,"Percent your skill cooldowns are shortened."),
            Map.entry(DerivedAttributes.Attr.LOOT_CHANCE,     "Extra probability to find rare items in containers."),
            Map.entry(DerivedAttributes.Attr.CRIT_CHANCE,     "Chance that any hit is a critical strike.")
        );

    /** Una línea breve explicativa del atributo derivado. */
    public static String attrTooltip(DerivedAttributes.Attr a) {
        return ATTR_DESCS.getOrDefault(a, a.name());
    }

    /**
     * Genera el texto para un tooltip de skill (ahora mismo solo el label).
     */
    public static String skillTooltip(Skill s) {
        return s.label();
    }
}

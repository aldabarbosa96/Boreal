// core/src/main/java/com/boreal/ui/overlay/TooltipData.java
package com.boreal.ui.overlay;

import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.model.Skills.Skill;
import com.boreal.util.DerivedSkillsUtil;

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

    /**
     * Genera el texto completo para un tooltip de stat, con valor dinámico.
     */
    public static String statTooltip(PrimaryStats.Stat s, int value) {
        String desc = STAT_DESCRIPTIONS.getOrDefault(s, "");
        return String.format("%s: %d\n\n%s", s.label(), value, desc);
    }

    /**
     * Genera el texto completo para un tooltip de profesión, con skills + bonuses.
     */
    public static String professionTooltip(Professions.Type p) {
        // 1) Habilidades derivadas
        List<Skill> skills = DerivedSkillsUtil.getDerivedSkills(p);
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

    /**
     * Genera el texto para un tooltip de skill (ahora mismo solo el label).
     */
    public static String skillTooltip(Skill s) {
        return s.label();
    }
}

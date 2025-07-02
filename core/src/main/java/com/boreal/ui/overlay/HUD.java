package com.boreal.ui.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.model.DerivedAttributes;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.model.Skills;

import java.util.List;
import java.util.Map;

/**
 * HUD Win-95 con tres ventanas:
 * • Player   → nombre
 * • Stats    → stats primarias + atributos derivados
 * • Prof/Sk  → profesiones seleccionadas + skills derivadas
 */
public final class HUD extends Table {

    /*··· constantes de estilo ···*/
    private static final float HUD_WINDOW_WIDTH = 220f;
    private final Label.LabelStyle white;   // texto en blanco

    /*··· Player ···*/
    private final Window playerWin;
    private final Label nameValue;

    /*··· Stats & derived ···*/
    private final Window statsWin;
    private final Table statsTable;       // primarias
    private final Table derivedTable;     // derivadas (sangrado)

    /*··· Professions & skills ···*/
    private final Window profWin;
    private final Table profTable;        // profesiones
    private final Table skillTable;       // skills (sangrado)

    /*────────────────────────────── constructor ─────────────────────────────*/
    public HUD(Skin skin) {
        super(skin);
        setFillParent(true);
        top().left().pad(10);

        /* estilo “texto blanco” reutilizable */
        white = new Label.LabelStyle(skin.getFont("font-win95"), Color.WHITE);

        /*──────── Player ────────*/
        playerWin = makeWindow("Player", skin);
        nameValue = new Label("", skin, "win95-label-black");
        playerWin.add(nameValue).expandX().fillX().pad(4, 6, 4, 6).row();

        /*──────── Stats ─────────*/
        statsWin = makeWindow("Stats", skin);
        statsTable = new Table(skin);
        statsTable.defaults().left().pad(1);
        derivedTable = new Table(skin);
        derivedTable.defaults().left().pad(1).padLeft(12); // sangría

        ScrollPane stScroll = new ScrollPane(statsTable, skin);
        stScroll.setScrollingDisabled(false, false);
        stScroll.setFadeScrollBars(false);

        ScrollPane derScroll = new ScrollPane(derivedTable, skin);
        derScroll.setScrollingDisabled(false, false);
        derScroll.setFadeScrollBars(false);

        statsWin.add(stScroll).expand().fill().pad(4, 6, 2, 6).row();
        statsWin.add(derScroll).expand().fill().pad(0, 6, 4, 6).row();

        /*──────── Prof + skills ─*/
        profWin = makeWindow("Professions", skin);
        profTable = new Table(skin);
        profTable.defaults().left().pad(1);
        skillTable = new Table(skin);
        skillTable.defaults().left().pad(1).padLeft(12);

        ScrollPane prScroll = new ScrollPane(profTable, skin);
        prScroll.setScrollingDisabled(false, false);
        prScroll.setFadeScrollBars(false);

        ScrollPane skScroll = new ScrollPane(skillTable, skin);
        skScroll.setScrollingDisabled(false, false);
        skScroll.setFadeScrollBars(false);

        profWin.add(prScroll).expand().fill().pad(4, 6, 2, 6).row();
        profWin.add(skScroll).expand().fill().pad(0, 6, 4, 6).row();

        /*──────── layout global ─*/
        add(playerWin).width(HUD_WINDOW_WIDTH).left().row();
        add(statsWin).width(HUD_WINDOW_WIDTH).left().row();
        add(profWin).width(HUD_WINDOW_WIDTH).left().row();
    }

    /*──────────────────────────── API pública ───────────────────────────────*/

    /**
     * Nombre del jugador (cadena vacía para limpiar).
     */
    public void setPlayerName(String name) {
        nameValue.setText(name == null ? "" : name);
    }

    /**
     * Stats primarias **y** derivadas en un mismo recuadro.
     */
    public void setStatsAndDerived(Map<PrimaryStats.Stat, Integer> stats, Map<DerivedAttributes.Attr, Float> derived) {
        statsTable.clear();
        derivedTable.clear();
        if (stats != null && !stats.isEmpty()) {
            for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
                Label l = new Label(" » " + s.label() + ": " + stats.getOrDefault(s, 0), white);
                statsTable.add(l).expandX().fillX().row();
            }
        }
        if (derived != null && !derived.isEmpty()) {
            for (DerivedAttributes.Attr a : DerivedAttributes.Attr.values()) {
                float v = derived.getOrDefault(a, 0f);
                String txt = String.format("· %s: %s", a.name().replace('_', ' '), (v % 1 == 0) ? String.valueOf((int) v) : String.format("%.1f", v));
                Label l = new Label(txt, white);
                derivedTable.add(l).expandX().fillX().row();
            }
        }
    }

    /**
     * Profesiones + skills derivadas en el mismo recuadro.
     */
    public void setProfessionsAndSkills(List<Professions.Type> profs, List<Skills.Skill> skills) {
        profTable.clear();
        skillTable.clear();
        if (profs != null && !profs.isEmpty()) {
            for (Professions.Type p : profs) {
                Label l = new Label(" » " + p.label(), white);
                profTable.add(l).expandX().fillX().row();
            }
        }
        if (skills != null && !skills.isEmpty()) {
            for (Skills.Skill s : skills) {
                Label l = new Label("· " + s.label(), white);
                skillTable.add(l).expandX().fillX().row();
            }
        }
    }

    /*──────────────────────── helpers UI ─────────────────────────*/
    private Window makeWindow(String title, Skin skin) {
        Window.WindowStyle ws = new Window.WindowStyle(skin.getFont("font-win95"), Color.BLACK, new NinePatchDrawable(makeFrame()));
        Window w = new Window("", ws);
        w.pad(0).padTop(2).align(Align.topLeft);

        Table header = new Table(skin);
        header.setBackground(new TextureRegionDrawable(makeTitleBg()));
        Label t = new Label(title, skin, "win95-title-label");
        t.setAlignment(Align.left);
        header.add(t).left().expandX().fillX().pad(4, 6, 4, 6);

        w.add(header).expandX().fillX().pad(-2, -2, 2, -2).row();
        return w;
    }

    /* Marco & cabecera idénticos al resto de la UI */
    private NinePatch makeFrame() {
        Pixmap pm = new Pixmap(32, 32, Format.RGBA8888);
        pm.setColor(Color.valueOf("C0C0C0"));
        pm.fill();
        pm.setColor(Color.valueOf("FFFFFF"));
        pm.drawLine(0, 0, 31, 0);
        pm.drawLine(0, 1, 0, 31);
        pm.setColor(Color.valueOf("404040"));
        pm.drawLine(0, 31, 31, 31);
        pm.drawLine(31, 0, 31, 31);
        pm.setColor(Color.valueOf("808080"));
        pm.drawRectangle(1, 1, 30, 30);
        NinePatch n = new NinePatch(new Texture(pm), 6, 6, 6, 6);
        pm.dispose();
        return n;
    }

    private Texture makeTitleBg() {
        Pixmap pm = new Pixmap(8, 30, Format.RGBA8888);
        pm.setColor(Color.valueOf("000080"));
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }
}

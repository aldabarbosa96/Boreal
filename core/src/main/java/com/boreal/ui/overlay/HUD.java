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
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.model.Skills;

import java.util.List;
import java.util.Map;

/**
 * HUD lateral con secciones en ScrollPane y texto en blanco,
 * usando un guión ASCII para las viñetas, y nombre del jugador en negro.
 */
public class HUD extends Table {
    private static final float HUD_WINDOW_WIDTH = 200f;
    private final Window playerWin;
    private final Label nameValue;
    private final Window statsWin;
    private final Table statsTable;
    private final Window profWin;
    private final Table profTable;
    private final Window habWin;
    private final Table habTable;

    // estilo para texto en blanco
    private final Label.LabelStyle whiteStyle;

    public HUD(Skin skin) {
        super(skin);

        // Creamos un LabelStyle con la fuente Win95 y color blanco
        whiteStyle = new Label.LabelStyle(skin.getFont("font-win95"), Color.WHITE);

        setFillParent(true);
        top().left();
        pad(10);

        // — Player —
        playerWin = createWindow("Player", skin);
        nameValue = new Label("", skin, "win95-label-black");  // mantiene negro
        playerWin.add(nameValue).expandX().fillX().pad(4, 6, 4, 6).align(Align.left).row();

        // — Stats —
        statsWin = createWindow("Stats", skin);
        statsTable = new Table(skin);
        statsTable.defaults().left().pad(2);
        ScrollPane statsScroll = new ScrollPane(statsTable, skin);
        statsScroll.setFadeScrollBars(false);
        statsScroll.setScrollingDisabled(false, false);
        statsWin.add(statsScroll).expand().fill().pad(4, 6, 4, 6).row();

        // — Professions —
        profWin = createWindow("Professions", skin);
        profTable = new Table(skin);
        profTable.defaults().left().pad(2);
        ScrollPane profScroll = new ScrollPane(profTable, skin);
        profScroll.setFadeScrollBars(false);
        profScroll.setScrollingDisabled(false, false);
        profWin.add(profScroll).expand().fill().pad(4, 6, 4, 6).row();

        // — Habilities —
        habWin = createWindow("Habilities", skin);
        habTable = new Table(skin);
        habTable.defaults().left().pad(2);
        ScrollPane habScroll = new ScrollPane(habTable, skin);
        habScroll.setFadeScrollBars(false);
        habScroll.setScrollingDisabled(false, false);
        habWin.add(habScroll).expand().fill().pad(4, 6, 4, 6).row();

        // Agregamos las cuatro ventanas con ancho fijo
        add(playerWin).width(HUD_WINDOW_WIDTH).left().row();
        add(statsWin).width(HUD_WINDOW_WIDTH).left().row();
        add(profWin).width(HUD_WINDOW_WIDTH).left().row();
        add(habWin).width(HUD_WINDOW_WIDTH).left().row();
    }

    private Window createWindow(String titleText, Skin skin) {
        Window.WindowStyle ws = new Window.WindowStyle(skin.getFont("font-win95"), Color.BLACK, new NinePatchDrawable(makeWin95Frame()));
        Window win = new Window("", ws);
        win.pad(0);
        win.padTop(2);
        win.align(Align.topLeft);

        Table header = new Table(skin);
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label(titleText, skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);

        win.add(header).expandX().fillX().pad(-2, -2, 2, -2);
        win.row();

        return win;
    }

    /**
     * Solo muestra nombre, puede ser cadena vacía.
     */
    public void setPlayerName(String name) {
        nameValue.setText(name != null ? name : "");
    }

    /**
     * Rellena statsTable solo si stats no está vacío.
     */
    public void setStats(Map<PrimaryStats.Stat, Integer> stats) {
        statsTable.clear();
        if (stats == null || stats.isEmpty()) return;
        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
            Label line = new Label("- " + s.label() + ": " + stats.getOrDefault(s, 0), whiteStyle);
            line.setWrap(true);
            line.setAlignment(Align.left);
            statsTable.add(line).expandX().fillX().pad(2).row();
        }
    }

    /**
     * Rellena profTable solo si la lista no está vacía.
     */
    public void setProfessions(List<Professions.Type> profs) {
        profTable.clear();
        if (profs == null || profs.isEmpty()) return;
        for (Professions.Type p : profs) {
            Label line = new Label("- " + p.label(), whiteStyle);
            line.setWrap(true);
            line.setAlignment(Align.left);
            profTable.add(line).expandX().fillX().pad(2).row();
        }
    }

    /**
     * Rellena habTable solo si la lista no está vacía.
     */
    public void setHabilities(List<Skills.Skill> habs) {
        habTable.clear();
        if (habs == null || habs.isEmpty()) return;
        for (Skills.Skill h : habs) {
            Label line = new Label("- " + h.label(), whiteStyle);
            line.setWrap(true);
            line.setAlignment(Align.left);
            habTable.add(line).expandX().fillX().pad(2).row();
        }
    }

    // — Helpers para marco y fondo de cabecera — //

    private NinePatch makeWin95Frame() {
        Pixmap pm = new Pixmap(64, 64, Format.RGBA8888);
        pm.setColor(Color.valueOf("C0C0C0"));
        pm.fill();
        pm.setColor(Color.valueOf("FFFFFF"));
        pm.drawLine(0, 0, 63, 0);
        pm.drawLine(0, 1, 0, 63);
        pm.setColor(Color.valueOf("404040"));
        pm.drawLine(0, 63, 63, 63);
        pm.drawLine(63, 0, 63, 63);
        pm.setColor(Color.valueOf("808080"));
        pm.drawRectangle(1, 1, 62, 62);
        NinePatch np = new NinePatch(new Texture(pm), 6, 6, 6, 6);
        pm.dispose();
        return np;
    }

    private Texture makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 30, Format.RGBA8888);
        pm.setColor(Color.valueOf("000080"));
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }
}

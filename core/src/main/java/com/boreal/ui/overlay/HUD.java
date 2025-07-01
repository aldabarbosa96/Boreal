// core/src/main/java/com/boreal/ui/overlay/HUD.java
package com.boreal.ui.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;

import java.util.List;
import java.util.Map;

/**
 * HUD lateral. Cada sección permanece VACÍA hasta que se llame a su setter
 * con datos no vacíos. Ahora cada panel y sus líneas ocupan todo el ancho
 * y quedan pegadas al borde izquierdo.
 */
public class HUD extends Table {
    private static final float HUD_WINDOW_WIDTH = 175f;

    private final Window playerWin;
    private final Label nameValue;
    private final Window statsWin;
    private final Table statsTable;
    private final Window profWin;
    private final Table profTable;
    private final Window habWin;
    private final Table habTable;

    public HUD(Skin skin) {
        super(skin);

        setFillParent(true);
        top().left();
        pad(10);

        // — Player —
        playerWin = createWindow("Player", skin);
        nameValue = new Label("", skin, "win95-label-black");
        nameValue.setColor(Color.WHITE);
        playerWin.add(nameValue)
            .expandX().fillX()
            .pad(4, 6, 4, 6)
            .align(Align.left)
            .row();

        // — Stats —
        statsWin = createWindow("Stats", skin);
        statsTable = new Table(skin);
        statsTable.defaults().left().pad(2);
        statsWin.add(statsTable)
            .expandX().fillX()
            .pad(4, 6, 4, 6)
            .align(Align.left)
            .row();

        // — Professions —
        profWin = createWindow("Professions", skin);
        profTable = new Table(skin);
        profTable.defaults().left().pad(2);
        profWin.add(profTable)
            .expandX().fillX()
            .pad(4, 6, 4, 6)
            .align(Align.left)
            .row();

        // — Habilities —
        habWin = createWindow("Habilities", skin);
        habTable = new Table(skin);
        habTable.defaults().left().pad(2);
        habWin.add(habTable)
            .expandX().fillX()
            .pad(4, 6, 4, 6)
            .align(Align.left)
            .row();

        // Ahora los cuatro paneles ocupan todo el ancho y alinean su contenido a la izquierda
        add(playerWin).width(HUD_WINDOW_WIDTH).left().row();
        add(statsWin).width(HUD_WINDOW_WIDTH).left().row();
        add(profWin).width(HUD_WINDOW_WIDTH).left().row();
        add(habWin).width(HUD_WINDOW_WIDTH).left().row();
    }

    private Window createWindow(String titleText, Skin skin) {
        Window.WindowStyle ws = new Window.WindowStyle(
            skin.getFont("font-win95"),
            Color.BLACK,
            new NinePatchDrawable(makeWin95Frame())
        );
        Window win = new Window("", ws);
        win.pad(0);
        win.padTop(2);
        // Asegura que todo el contenido empiece en la esquina superior izquierda
        win.align(Align.topLeft);

        Table header = new Table(skin);
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label(titleText, skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title)
            .left().expandX().fillX()
            .pad(4, 6, 4, 6);

        win.add(header)
            .expandX().fillX()
            .pad(-2, -2, 2, -2);
        win.row();

        return win;
    }

    /** Solo muestra nombre, puede ser cadena vacía. */
    public void setPlayerName(String name) {
        nameValue.setText(name);
    }

    /**
     * Rellena statsTable **solo** si stats no está vacío.
     */
    public void setStats(Map<PrimaryStats.Stat, Integer> stats) {
        statsTable.clear();
        if (stats == null || stats.isEmpty()) return;
        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
            Label line = new Label(s.label() + ": " + stats.getOrDefault(s, 0), getSkin(), "win95-label-black");
            line.setColor(Color.WHITE);
            line.setAlignment(Align.left);
            statsTable.add(line)
                .expandX().fillX()
                .pad(2)
                .align(Align.left)
                .row();
        }
    }

    /**
     * Rellena profTable **solo** si la lista no está vacía.
     */
    public void setProfessions(List<Professions.Type> profs) {
        profTable.clear();
        if (profs == null || profs.isEmpty()) return;
        for (Professions.Type p : profs) {
            Label line = new Label(p.label(), getSkin(), "win95-label-black");
            line.setColor(Color.WHITE);
            line.setAlignment(Align.left);
            profTable.add(line)
                .expandX().fillX()
                .pad(2)
                .align(Align.left)
                .row();
        }
    }

    /**
     * Rellena habTable **solo** si la lista no está vacía.
     */
    public void setHabilities(List<String> habs) {
        habTable.clear();
        if (habs == null || habs.isEmpty()) return;
        for (String h : habs) {
            Label line = new Label(h, getSkin(), "win95-label-black");
            line.setColor(Color.WHITE);
            line.setAlignment(Align.left);
            habTable.add(line)
                .expandX().fillX()
                .pad(2)
                .align(Align.left)
                .row();
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

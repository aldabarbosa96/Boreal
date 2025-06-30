// core/src/main/java/com/boreal/ui/overlay/HUD.java
package com.boreal.ui.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;

import java.util.List;
import java.util.Map;

public class HUD extends Table {
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

        // Ocupa todo el escenario y alinea arriba-izquierda
        setFillParent(true);
        top().left();
        pad(10);

        // Creamos cada sección como ventana Win95
        playerWin = createWindow("Player", skin);
        nameValue = new Label("", skin, "win95-label-black");
        nameValue.setColor(Color.WHITE);
        playerWin.add(nameValue).pad(4).row();

        statsWin = createWindow("Stats", skin);
        statsTable = new Table(skin);
        statsTable.defaults().left().pad(2);
        statsWin.add(statsTable).pad(4).row();

        profWin = createWindow("Professions", skin);
        profTable = new Table(skin);
        profTable.defaults().left().pad(2);
        profWin.add(profTable).pad(4).row();

        habWin = createWindow("Habilities", skin);
        habTable = new Table(skin);
        habTable.defaults().left().pad(2);
        habWin.add(habTable).pad(4).row();

        // Añadimos las ventanas en columna
        add(playerWin).left().row();
        add(statsWin).left().row();
        add(profWin).left().row();
        add(habWin).left().row();
    }

    private Window createWindow(String titleText, Skin skin) {
        // Estilo de ventana con marco NinePatch y fuente Win95
        Window.WindowStyle ws = new Window.WindowStyle(skin.getFont("font-win95-title"), Color.WHITE, new NinePatchDrawable(makeWin95Frame()));
        Window win = new Window(titleText, ws);

        // Personalizamos la barra de título
        Table titleTable = win.getTitleTable();
        titleTable.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        titleTable.padTop(4);
        titleTable.padBottom(4);
        titleTable.padLeft(6);
        titleTable.padRight(6);
        // (no chaining porque pad*() devuelve void)

        win.pad(0);
        win.padTop(2);
        return win;
    }

    public void setPlayerName(String name) {
        nameValue.setText(name);
    }

    public void setStats(Map<PrimaryStats.Stat, Integer> stats) {
        statsTable.clear();
        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
            Label line = new Label(s.label() + ": " + stats.getOrDefault(s, 0), getSkin(), "win95-label-black");
            line.setColor(Color.WHITE);
            statsTable.add(line).row();
        }
    }

    public void setProfessions(List<Professions.Type> profs) {
        profTable.clear();
        for (Professions.Type p : profs) {
            Label line = new Label(p.label(), getSkin(), "win95-label-black");
            line.setColor(Color.WHITE);
            profTable.add(line).row();
        }
    }

    public void setHabilities(List<String> habs) {
        habTable.clear();
        for (String h : habs) {
            Label line = new Label(h, getSkin(), "win95-label-black");
            line.setColor(Color.WHITE);
            habTable.add(line).row();
        }
    }

    // — Helpers para marco y barra de título —

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

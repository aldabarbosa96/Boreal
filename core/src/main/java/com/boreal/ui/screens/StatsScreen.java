package com.boreal.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boreal.model.PrimaryStats;

public final class StatsScreen extends ScreenAdapter {

    // ───────────────── Win95 system-palette constants ──────────────────
    private static final Color WIN95_FACE = Color.valueOf("C0C0C0"); // ButtonFace
    private static final Color WIN95_HIGHLIGHT = Color.valueOf("FFFFFF"); // ButtonHighlight
    private static final Color WIN95_SHADOW = Color.valueOf("808080"); // ButtonShadow
    private static final Color WIN95_DARKSHADOW = Color.valueOf("404040"); // ButtonDkShadow
    private static final Color WIN95_TEXT = Color.valueOf("000000"); // WindowText
    private static final Color WIN95_TITLE = Color.valueOf("000080"); // ActiveTitle
    // ────────────────────────────────────────────────────────────────────

    private final Stage stage;
    private final Skin skin;
    private final PrimaryStats stats;
    private final Runnable onAccept;

    private Label pointsLabel;
    private TextButton acceptBtn;

    public StatsScreen(Skin skin, PrimaryStats stats, Runnable onAccept) {
        this.skin = skin;
        this.stats = stats;
        this.onAccept = onAccept;

        // Añadimos la fuente Win95 al Skin recibido (sin romper estilos previos)
        ensureWin95Font(skin);

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildUI();
    }

    // ──────────────────────────── UI ───────────────────────────────────
    private void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table win95 = new Table();
        win95.pad(0).defaults().pad(6);
        win95.setBackground(makeWin95Frame());
        root.add(win95).center();

        // ---- Barra de título (azul sólido)
        Table header = new Table();
        header.setBackground(makeTitleBackground());
        pointsLabel = new Label("Puntos libres: " + stats.getRemainingPoints(), skin, "win95-label-white");
        pointsLabel.setAlignment(Align.center);
        header.add(pointsLabel).expandX().fillX().pad(6);
        win95.add(header).colspan(4).fillX();
        win95.row();

        // ---- Filas de stats
        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
            Label name = new Label(s.label(), skin, "win95-label-black");
            final Label value = new Label(String.valueOf(stats.get(s)), skin, "win95-label-blue");

            TextButton minus = new TextButton("- ", skin, "win95");
            TextButton plus = new TextButton("+", skin, "win95");

            ChangeListener refresher = new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    value.setText(Integer.toString(stats.get(s)));
                    pointsLabel.setText("Puntos libres: " + stats.getRemainingPoints());
                    acceptBtn.setDisabled(stats.getRemainingPoints() > 0);
                }
            };

            plus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.raise(s)) refresher.changed(e, a);
                }
            });
            minus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.lower(s)) refresher.changed(e, a);
                }
            });

            win95.add(name).left().width(140);
            win95.add(value).center().width(50);
            win95.add(minus).size(32, 32);
            win95.add(plus).size(32, 32);
            win95.row();
        }

        // ---- Botón Aceptar
        win95.row().padTop(12);
        acceptBtn = new TextButton("Aceptar", skin, "win95");
        acceptBtn.setDisabled(true);
        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                onAccept.run();
            }
        });
        win95.add(acceptBtn).colspan(4).width(120).center();
    }

    // ───────────────────────── Helpers ─────────────────────────────────

    /**
     * Añade “MS Sans Serif 8” (o Tahoma 8) y tres estilos de etiqueta + botón Win95
     */
    /**
     * Usa la fuente que ya exista en el Skin (o la default de LibGDX).
     */
    private void ensureWin95Font(Skin sk) {

        // 1. Recupera la fuente por defecto del Skin si existe; si no, crea una BitmapFont genérica
        BitmapFont win95Font;
        if (sk.has("default-font", BitmapFont.class)) {
            win95Font = sk.getFont("default-font");
        } else {
            win95Font = new BitmapFont();          // “Arial-like” bitmap que LibGDX genera en runtime
            sk.add("default-font", win95Font);     // la añadimos al Skin para futuros usos
        }

        // 2. Estilos de etiquetas (solo cambian colores)
        sk.add("win95-label-black", new Label.LabelStyle(win95Font, WIN95_TEXT));
        sk.add("win95-label-blue", new Label.LabelStyle(win95Font, WIN95_TITLE));
        sk.add("win95-label-white", new Label.LabelStyle(win95Font, Color.WHITE));

        // 3. Estilo de botón Win95 (borde relieve y hundido)
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle(makeBtnBg(false),   // up
            makeBtnBg(true),    // down
            null, null);
        tbs.font = win95Font;
        tbs.fontColor = WIN95_TEXT;
        sk.add("win95", tbs);
    }


    /**
     * Marco de ventana con bisel de 3 px (alto relieve clásico)
     */
    private NinePatchDrawable makeWin95Frame() {
        int s = 64, b = 6;
        Pixmap pm = new Pixmap(s, s, Pixmap.Format.RGBA8888);

        // cara
        pm.setColor(WIN95_FACE);
        pm.fill();

        // bisel exterior (Order: top-left highlight, bottom-right dark)
        pm.setColor(WIN95_HIGHLIGHT);
        pm.drawLine(0, 0, s - 1, 0);
        pm.drawLine(0, 1, 0, s - 1);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, s - 1, s - 1, s - 1);
        pm.drawLine(s - 1, 0, s - 1, s - 1);

        // bisel interior (sombras más suaves)
        pm.setColor(WIN95_SHADOW);
        pm.drawRectangle(1, 1, s - 2, s - 2);

        NinePatch np = new NinePatch(new Texture(pm), b, b, b, b);
        pm.dispose();
        return new NinePatchDrawable(np);
    }

    /**
     * Barra de título sólido
     */
    private Drawable makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 22, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_TITLE);
        pm.fill();
        Texture tex = new Texture(pm);
        pm.dispose();
        return new TextureRegionDrawable(tex);
    }

    /**
     * Crea fondo para el botón Win95 (up o down)
     */
    private NinePatchDrawable makeBtnBg(boolean pressed) {
        int s = 32, b = 4;
        Pixmap pm = new Pixmap(s, s, Pixmap.Format.RGBA8888);

        // Cara del botón
        pm.setColor(WIN95_FACE);
        pm.fill();

        if (!pressed) {
            // Borde alto relieve (no presionado)
            pm.setColor(WIN95_HIGHLIGHT);
        } else {
            // Borde hundido (presionado)
            pm.setColor(WIN95_SHADOW);
        }
        pm.drawLine(0, 0, s - 2, 0);
        pm.drawLine(0, 1, 0, s - 2);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, s - 1, s - 1, s - 1);
        pm.drawLine(s - 1, 0, s - 1, s - 2);

        NinePatch np = new NinePatch(new Texture(pm), b, b, b, b);
        pm.dispose();
        return new NinePatchDrawable(np);
    }

    // ────────────────────────── lifecycle ──────────────────────────────
    @Override
    public void render(float dt) {
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

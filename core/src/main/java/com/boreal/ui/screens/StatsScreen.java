package com.boreal.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boreal.model.PrimaryStats;

import java.util.EnumMap;

import static com.boreal.assets.GameAssets.manager;

public final class StatsScreen extends ScreenAdapter {

    // ───────────────── Win95 system-palette ─────────────────
    private static final Color WIN95_FACE = Color.valueOf("C0C0C0");
    private static final Color WIN95_HIGHLIGHT = Color.valueOf("FFFFFF");
    private static final Color WIN95_SHADOW = Color.valueOf("808080");
    private static final Color WIN95_DARKSHADOW = Color.valueOf("404040");
    private static final Color WIN95_TEXT = Color.valueOf("000000");
    private static final Color WIN95_TITLE = Color.valueOf("000080");
    // ────────────────────────────────────────────────────────

    private final Stage stage;
    private final Skin skin;
    private final PrimaryStats stats;
    private final Runnable onAccept;

    private Label pointsLabel;
    private TextButton acceptBtn;
    private TextButton resetBtn;

    private final EnumMap<PrimaryStats.Stat, Label> valueLabels = new EnumMap<>(PrimaryStats.Stat.class);

    // ───────────────────────── ctor ─────────────────────────
    public StatsScreen(Skin skin, PrimaryStats stats, Runnable onAccept) {
        this.skin = skin;
        this.stats = stats;
        this.onAccept = onAccept;

        ensureWin95Font(skin);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildUI();
        addEnterKeySupport();
        refreshControls();
    }

    // ────────────────────────── UI ─────────────────────────
    private void buildUI() {
        /*── Contenedor raíz ───────────────────────────────*/
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        /*── Marco Win95 ───────────────────────────────────*/
        Table win95 = new Table();
        win95.setBackground(makeWin95Frame());
        root.add(win95).center();

        /*── Barra de título ───────────────────────────────*/
        Table header = new Table();
        header.setBackground(makeTitleBackground());

        pointsLabel = new Label("Remaining points: " + stats.getRemainingPoints(), skin, "win95-title-label");
        pointsLabel.setAlignment(Align.left);
        header.add(pointsLabel).left().expandX().fillX().pad(4, 6, 4, 6);

        // ↑ ahora la ventana tendrá 5 columnas (icono + 4 anteriores)
        Cell<?> headerCell = win95.add(header).colspan(5).fillX();
        headerCell.pad(-2, -2, 2, -2);
        win95.row();

        win95.defaults().pad(8);

        /*── Tabla de estadísticas ─────────────────────────*/
        Table content = new Table();
        content.defaults().pad(8);

        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {

            // 1️⃣  Icono
            Texture iconTex = manager.get("icons/" + s.name().toLowerCase() + ".png", Texture.class);
            iconTex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); // pixel-perfect
            Image iconImg = new Image(new TextureRegionDrawable(iconTex));

            // 2️⃣  Nombre y valor de la stat
            Label name = new Label(s.label(), skin, "win95-label-black");
            Label value = new Label(String.valueOf(stats.get(s)), skin, "win95-label-blue");
            valueLabels.put(s, value);

            // 3️⃣  Botones ±
            TextButton minus = new TextButton("- ", skin, "win95");
            TextButton plus = new TextButton("+", skin, "win95");

            ChangeListener refresher = new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    refreshControls();
                }
            };

            plus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.raise(s)) {
                        value.setText(stats.get(s) + "");
                        refresher.changed(e, a);
                    }
                }
            });
            minus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.lower(s)) {
                        value.setText(stats.get(s) + "");
                        refresher.changed(e, a);
                    }
                }
            });

            /*── Añadir fila completa ──────────────────────*/
            content.add(iconImg).size(32, 32).padRight(6);
            content.add(name).left().width(120);
            content.add(value).center().width(50);
            content.add(minus).size(32, 32);
            content.add(plus).size(32, 32);
            content.row();
        }

        /*── Fila de botones ───────────────────────────────*/
        content.row().padTop(24).padBottom(12);

        resetBtn = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");

        resetBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                stats.reset();
                for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
                    valueLabels.get(s).setText(String.valueOf(stats.get(s)));
                }
                refreshControls();
            }
        });
        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                onAccept.run();
            }
        });

        Table btnRow = new Table();
        btnRow.add(resetBtn).width(120).padRight(8);
        btnRow.add(acceptBtn).width(120);

        content.add(btnRow).colspan(5).center();

        /*── Márgenes interiores ───────────────────────────*/
        win95.row();
        win95.add(content).colspan(5).padLeft(12).padRight(12).padBottom(12).fillX();
    }


    /*────────── Utilidades de refresco y teclado ──────────*/
    private void refreshControls() {
        pointsLabel.setText("Remaining points: " + stats.getRemainingPoints());
        boolean ready = stats.getRemainingPoints() == 0;
        acceptBtn.setDisabled(!ready);

        // marco negro sólo si es la acción predeterminada
        String style = ready ? "win95-default" : "win95";
        acceptBtn.setStyle(skin.get(style, TextButton.TextButtonStyle.class));
    }

    private void addEnterKeySupport() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent e, int keycode) {
                if (keycode == Input.Keys.ENTER && !acceptBtn.isDisabled()) {
                    acceptBtn.fire(new ChangeListener.ChangeEvent());
                    return true;
                }
                return false;
            }
        });
    }

    /*───────────── Registro de fuentes y estilos ───────────*/
    private void ensureWin95Font(Skin sk) {
        if (sk.has("font-win95", BitmapFont.class) && sk.has("font-win95-title", BitmapFont.class)) return;

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/IBMPlexSans-Regular.ttf"));

        FreeTypeFontParameter p14 = new FreeTypeFontParameter();
        p14.size = 14;
        p14.hinting = FreeTypeFontGenerator.Hinting.None;
        p14.minFilter = TextureFilter.Nearest;
        p14.magFilter = TextureFilter.Nearest;
        BitmapFont font14 = gen.generateFont(p14);

        FreeTypeFontParameter p18 = new FreeTypeFontParameter();
        p18.size = 18;
        p18.hinting = FreeTypeFontGenerator.Hinting.None;
        p18.minFilter = TextureFilter.Nearest;
        p18.magFilter = TextureFilter.Nearest;
        BitmapFont font18 = gen.generateFont(p18);

        gen.dispose();

        sk.add("font-win95", font14, BitmapFont.class);
        sk.add("font-win95-title", font18, BitmapFont.class);

        sk.add("win95-label-black", new Label.LabelStyle(font14, WIN95_TEXT));
        sk.add("win95-label-blue", new Label.LabelStyle(font14, WIN95_TITLE));
        sk.add("win95-label-white", new Label.LabelStyle(font14, Color.WHITE));
        sk.add("win95-title-label", new Label.LabelStyle(font18, Color.WHITE));

        TextButton.TextButtonStyle normal = new TextButton.TextButtonStyle(makeBtnBg(false), makeBtnBg(true), null, null);
        normal.font = font14;
        normal.fontColor = WIN95_TEXT;
        sk.add("win95", normal);

        TextButton.TextButtonStyle def = new TextButton.TextButtonStyle(makeBtnBgDefault(false), makeBtnBgDefault(true), null, null);
        def.font = font14;
        def.fontColor = WIN95_TEXT;
        sk.add("win95-default", def);
    }

    /*───────── Fabricas de gráficos Win95 ─────────────────*/
    private NinePatchDrawable makeWin95Frame() {
        int S = 64, B = 6;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);

        pm.setColor(WIN95_FACE);
        pm.fill();
        pm.setColor(WIN95_HIGHLIGHT);
        pm.drawLine(0, 0, S - 1, 0);
        pm.drawLine(0, 1, 0, S - 1);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, S - 1, S - 1, S - 1);
        pm.drawLine(S - 1, 0, S - 1, S - 1);
        pm.setColor(WIN95_SHADOW);
        pm.drawRectangle(1, 1, S - 2, S - 2);

        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return new NinePatchDrawable(np);               // no dispose: Texture se encarga
    }

    private Drawable makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 30, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_TITLE);
        pm.fill();
        Texture tex = new Texture(pm);
        return new TextureRegionDrawable(tex);
    }

    private NinePatchDrawable makeBtnBg(boolean pressed) {
        int S = 32, B = 4;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);

        pm.setColor(WIN95_FACE);
        pm.fill();

        pm.setColor(!pressed ? WIN95_HIGHLIGHT : WIN95_SHADOW);
        pm.drawLine(0, 0, S - 2, 0);
        pm.drawLine(0, 1, 0, S - 2);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, S - 1, S - 1, S - 1);
        pm.drawLine(S - 1, 0, S - 1, S - 2);

        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return new NinePatchDrawable(np);
    }

    /**
     * Botón predeterminado: mismo relieve con borde negro exterior
     */
    private NinePatchDrawable makeBtnBgDefault(boolean pressed) {
        int S = 34, B = 4;                               // +2 px ancho/alto
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);

        // marco negro exterior
        pm.setColor(Color.BLACK);
        pm.fill();

        // fondo gris del botón dentro del marco
        pm.setColor(WIN95_FACE);
        pm.fillRectangle(1, 1, S - 2, S - 2);

        // relieve interior
        if (!pressed) {
            pm.setColor(WIN95_HIGHLIGHT);
            pm.drawLine(1, 1, S - 3, 1);
            pm.drawLine(1, 2, 1, S - 3);
        } else {
            pm.setColor(WIN95_SHADOW);
            pm.drawLine(1, 1, S - 3, 1);
            pm.drawLine(1, 2, 1, S - 3);
        }
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(1, S - 2, S - 2, S - 2);
        pm.drawLine(S - 2, 1, S - 2, S - 2);

        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return new NinePatchDrawable(np);               // Texture gestiona el Pixmap
    }

    // ───────────── lifecycle ──────────────────────────────
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

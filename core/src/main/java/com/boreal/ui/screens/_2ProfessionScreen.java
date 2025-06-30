package com.boreal.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;

import java.io.Serializable;
import java.util.function.Consumer;

import static com.boreal.assets.AssetManager.manager;

/**
 * Pantalla de selección de oficio (professions) en estilo Win95.
 * Al elegir uno, invoca el callback con el tipo seleccionado.
 */
public final class _2ProfessionScreen extends ScreenAdapter implements Serializable {

    // ───────────────── Win95 system‐palette ─────────────────
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
    private final String playerName;
    private final Consumer<Professions.Type> onProfessionChosen;

    public _2ProfessionScreen(Skin skin, PrimaryStats stats, String playerName, Consumer<Professions.Type> onProfessionChosen) {
        this.skin = skin;
        this.stats = stats;
        this.playerName = playerName;
        this.onProfessionChosen = onProfessionChosen;

        ensureWin95Styles(skin);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildUI();
        addEnterKeySupport();
    }

    private void buildUI() {
        // 1) Root container
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // 2) Win95 frame
        Table win95 = new Table();
        win95.setBackground(new NinePatchDrawable(makeWin95Frame()));
        root.add(win95).center();
        win95.row();

        // 3) Header
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Jugador: " + playerName + "    Elige oficio", skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).colspan(7).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // 4) Determine button width by widest label
        BitmapFont font = skin.get("font-win95", BitmapFont.class);
        GlyphLayout layout = new GlyphLayout();
        float maxWidth = 0;
        for (Professions.Type t : Professions.Type.values()) {
            layout.setText(font, t.label());
            maxWidth = Math.max(maxWidth, layout.width);
        }
        float buttonWidth = maxWidth + 20;

        // 5) Content grid
        Table content = new Table();
        content.defaults().pad(8);

        Professions.Type[] types = Professions.Type.values();
        for (int i = 0; i < types.length; i++) {
            Professions.Type t = types[i];

            // Icon
            Image icon = null;
            String iconPath = t.iconPath();
            if (manager.isLoaded(iconPath, Texture.class)) {
                Texture tex = manager.get(iconPath, Texture.class);
                tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
                icon = new Image(new TextureRegionDrawable(tex));
            }

            // Button
            TextButton btn = new TextButton(t.label(), skin, "win95");
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    onProfessionChosen.accept(t);
                }
            });

            // Cell container
            Table cell = new Table();
            if (icon != null) {
                cell.add(icon).size(24, 24).padRight(6);
            }
            cell.add(btn).width(buttonWidth).height(32);

            content.add(cell);
            if ((i + 1) % 7 == 0) content.row();
        }

        win95.add(content).colspan(7).pad(12).fillX();
    }

    private void addEnterKeySupport() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // ───────────── Win95 UI factories ────────────────────────────────

    private void ensureWin95Styles(Skin sk) {
        if (sk.has("font-win95", BitmapFont.class) && sk.has("font-win95-title", BitmapFont.class) && sk.has("win95", TextButton.TextButtonStyle.class) && sk.has("win95-default", TextButton.TextButtonStyle.class)) {
            return;
        }

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
        sk.add("win95-title-label", new Label.LabelStyle(font18, Color.WHITE));

        NinePatchDrawable up = new NinePatchDrawable(makeBtnBg(false));
        NinePatchDrawable down = new NinePatchDrawable(makeBtnBg(true));
        TextButton.TextButtonStyle normal = new TextButton.TextButtonStyle(up, down, null, null);
        normal.font = font14;
        normal.fontColor = WIN95_TEXT;
        sk.add("win95", normal);

        NinePatchDrawable upDef = new NinePatchDrawable(makeBtnBgDefault(false));
        NinePatchDrawable downDef = new NinePatchDrawable(makeBtnBgDefault(true));
        TextButton.TextButtonStyle def = new TextButton.TextButtonStyle(upDef, downDef, null, null);
        def.font = font14;
        def.fontColor = WIN95_TEXT;
        sk.add("win95-default", def);
    }

    private NinePatch makeWin95Frame() {
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
        return np;
    }

    private Texture makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 30, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_TITLE);
        pm.fill();
        return new Texture(pm);
    }

    private NinePatch makeBtnBg(boolean pressed) {
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
        return np;
    }

    private NinePatch makeBtnBgDefault(boolean pressed) {
        int S = 34, B = 4;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);
        pm.setColor(Color.BLACK);
        pm.fill();
        pm.setColor(WIN95_FACE);
        pm.fillRectangle(1, 1, S - 2, S - 2);
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
        return np;
    }
}

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
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.function.Consumer;

public class NameScreen extends ScreenAdapter {

    // ───────────────── Win95 system‐palette ─────────────────
    private static final Color WIN95_FACE       = Color.valueOf("C0C0C0");
    private static final Color WIN95_HIGHLIGHT  = Color.valueOf("FFFFFF");
    private static final Color WIN95_SHADOW     = Color.valueOf("808080");
    private static final Color WIN95_DARKSHADOW = Color.valueOf("404040");
    private static final Color WIN95_TEXT       = Color.valueOf("000000");
    private static final Color WIN95_TITLE      = Color.valueOf("000080");
    // ────────────────────────────────────────────────────────

    private final Stage stage;
    private final Skin skin;
    private final Consumer<String> onNameEntered;

    public NameScreen(Skin skin, Consumer<String> onNameEntered) {
        this.skin = skin;
        this.onNameEntered = onNameEntered;
        ensureWin95Styles(skin);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildUI();
        addEnterKeySubmit();
    }

    private void buildUI() {
        // root container
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // Win95 window frame
        Table win95 = new Table();
        win95.setBackground(new NinePatchDrawable(makeWin95Frame()));
        root.add(win95).center();
        win95.row();

        // title bar (con padding igual al de StatsScreen)
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Enter your name", skin, "win95-title-label");
        header.add(title)
            .left()
            .expandX()
            .fillX()
            .pad(4, 6, 4, 6);
        win95.add(header)
            .fillX()
            .pad(-2, -2, 2, -2);
        win95.row();

        // content area
        Table content = new Table();
        content.defaults().pad(8);

        // text field
        final TextField nameField = new TextField("", skin, "win95-textfield");
        nameField.setMessageText("Type here...");
        content.add(nameField).width(300).row();

        // error label
        final Label errorLabel = new Label("", skin, "win95-label-blue");
        content.add(errorLabel).row();

        // button
        TextButton nextBtn = new TextButton("Accept", skin, "win95");
        content.add(nextBtn).row();

        win95.add(content).pad(12).fillX();

        nextBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    errorLabel.setText("Name cannot be empty.");
                } else {
                    onNameEntered.accept(name);
                }
            }
        });
    }

    private void addEnterKeySubmit() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    // dispara el primer TextButton encontrado
                    for (Actor a : stage.getActors()) {
                        if (a instanceof Table) {
                            for (Actor child : ((Table)a).getChildren()) {
                                if (child instanceof TextButton) {
                                    ((TextButton) child).fire(new ChangeListener.ChangeEvent());
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    // ───────────────────────── Win95 UI factories ─────────────────────────

    private void ensureWin95Styles(Skin sk) {
        if (sk.has("font-win95", BitmapFont.class)) return;

        FreeTypeFontGenerator gen =
            new FreeTypeFontGenerator(Gdx.files.internal("fonts/IBMPlexSans-Regular.ttf"));
        FreeTypeFontParameter p14 = new FreeTypeFontParameter();
        p14.size = 14;
        p14.minFilter = TextureFilter.Nearest;
        p14.magFilter = TextureFilter.Nearest;
        BitmapFont font14 = gen.generateFont(p14);

        FreeTypeFontParameter p18 = new FreeTypeFontParameter();
        p18.size = 18;
        p18.minFilter = TextureFilter.Nearest;
        p18.magFilter = TextureFilter.Nearest;
        BitmapFont font18 = gen.generateFont(p18);

        gen.dispose();

        sk.add("font-win95", font14, BitmapFont.class);
        sk.add("font-win95-title", font18, BitmapFont.class);

        sk.add("win95-label-black", new Label.LabelStyle(font14, WIN95_TEXT));
        sk.add("win95-label-blue", new Label.LabelStyle(font14, WIN95_TITLE));
        sk.add("win95-title-label", new Label.LabelStyle(font18, Color.WHITE));

        // Button styles
        NinePatchDrawable up   = new NinePatchDrawable(makeBtnBg(false));
        NinePatchDrawable down = new NinePatchDrawable(makeBtnBg(true));
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle(up, down, null, null);
        btnStyle.font = font14;
        btnStyle.fontColor = WIN95_TEXT;
        sk.add("win95", btnStyle);

        // TextField style with placeholder color = WIN95_SHADOW
        NinePatchDrawable tfBg = new NinePatchDrawable(makeTextFieldBg());
        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = font14;
        tfStyle.fontColor = WIN95_TEXT;
        tfStyle.messageFontColor = WIN95_SHADOW; // placeholder en gris Win95
        tfStyle.cursor = new TextureRegionDrawable(makeCursor());
        tfStyle.background = tfBg;
        sk.add("win95-textfield", tfStyle);
    }

    private NinePatch makeWin95Frame() {
        int S = 64, B = 6;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_FACE); pm.fill();
        pm.setColor(WIN95_HIGHLIGHT);
        pm.drawLine(0, 0, S - 1, 0); pm.drawLine(0, 1, 0, S - 1);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, S - 1, S - 1, S - 1); pm.drawLine(S - 1, 0, S - 1, S - 1);
        pm.setColor(WIN95_SHADOW);
        pm.drawRectangle(1, 1, S - 2, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return np;
    }

    private Texture makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 30, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_TITLE); pm.fill();
        return new Texture(pm);
    }

    private NinePatch makeBtnBg(boolean pressed) {
        int S = 32, B = 4;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_FACE); pm.fill();
        pm.setColor(!pressed ? WIN95_HIGHLIGHT : WIN95_SHADOW);
        pm.drawLine(0, 0, S - 2, 0); pm.drawLine(0, 1, 0, S - 2);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, S - 1, S - 1, S - 1); pm.drawLine(S - 1, 0, S - 1, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return np;
    }

    private NinePatch makeTextFieldBg() {
        int W = 200, H = 24, B = 4;
        Pixmap pm = new Pixmap(W, H, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_FACE); pm.fill();
        pm.setColor(WIN95_HIGHLIGHT);
        pm.drawLine(0, 0, W - 1, 0); pm.drawLine(0, 1, 0, H - 1);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, H - 1, W - 1, H - 1); pm.drawLine(W - 1, 0, W - 1, H - 1);
        pm.setColor(WIN95_SHADOW);
        pm.drawRectangle(1, 1, W - 2, H - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return np;
    }

    private Texture makeCursor() {
        Pixmap pm = new Pixmap(2, 16, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_TEXT); pm.fill();
        return new Texture(pm);
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
}

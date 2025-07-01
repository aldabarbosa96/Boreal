// core/src/main/java/com/boreal/ui/screens/_0Win95Screen.java
package com.boreal.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boreal.ui.overlay.HUD;

public abstract class _0Win95Screen extends ScreenAdapter {
    private static final Color WIN95_FACE       = Color.valueOf("C0C0C0");
    private static final Color WIN95_HIGHLIGHT  = Color.valueOf("FFFFFF");
    private static final Color WIN95_SHADOW     = Color.valueOf("808080");
    private static final Color WIN95_DARKSHADOW = Color.valueOf("404040");
    private static final Color WIN95_TEXT       = Color.valueOf("000000");
    private static final Color WIN95_TITLE      = Color.valueOf("000080");

    protected final Stage stage;
    protected final Skin skin;
    protected final Table windowFrame;
    protected final HUD hud;
    private boolean built = false;

    public _0Win95Screen(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        ensureWin95Styles(skin);

        // root y ventana principal
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        windowFrame = new Table();
        windowFrame.setBackground(new NinePatchDrawable(makeWin95Frame()));
        root.add(windowFrame).center();
        windowFrame.row();

        // creamos y añadimos el HUD como otra "ventana"
        hud = new HUD(skin);
        stage.addActor(hud);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        super.show();
        if (!built) {
            buildContent(windowFrame);
            built = true;
        }
    }

    protected abstract void buildContent(Table win95);

    @Override
    public void render(float delta) {
        stage.act(delta);
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

    protected void enableEnterSubmit() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    TextButton btn = findFirstButton(stage.getRoot());
                    if (btn != null) {
                        btn.fire(new ChangeEvent());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    protected void enableEscapeToExit() {
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

    private TextButton findFirstButton(Actor actor) {
        if (actor instanceof TextButton) return (TextButton) actor;
        if (actor instanceof Group) {
            for (Actor child : ((Group) actor).getChildren()) {
                TextButton found = findFirstButton(child);
                if (found != null) return found;
            }
        }
        return null;
    }

    private void ensureWin95Styles(Skin sk) {
        if (sk.has("font-win95", BitmapFont.class)) return;

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/IBMPlexSans-Regular.ttf"));
        FreeTypeFontParameter p14 = new FreeTypeFontParameter();
        p14.size = 14; p14.minFilter = TextureFilter.Nearest; p14.magFilter = TextureFilter.Nearest;
        FreeTypeFontParameter p18 = new FreeTypeFontParameter();
        p18.size = 18; p18.minFilter = TextureFilter.Nearest; p18.magFilter = TextureFilter.Nearest;
        BitmapFont font14 = gen.generateFont(p14);
        BitmapFont font18 = gen.generateFont(p18);
        gen.dispose();

        sk.add("font-win95", font14, BitmapFont.class);
        sk.add("font-win95-title", font18, BitmapFont.class);
        sk.add("win95-label-black", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font14, WIN95_TEXT));
        sk.add("win95-label-blue",  new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font14, WIN95_TITLE));
        sk.add("win95-title-label", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font18, Color.WHITE));

        TextButtonStyle btnStyle = new TextButtonStyle(
            new NinePatchDrawable(makeBtnBg(false)),
            new NinePatchDrawable(makeBtnBg(true)),
            null,
            font14
        );
        btnStyle.fontColor = WIN95_TEXT;
        sk.add("win95", btnStyle);

        com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle tfStyle =
            new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle();
        tfStyle.font             = font14;
        tfStyle.fontColor        = WIN95_TEXT;
        tfStyle.messageFontColor = WIN95_SHADOW;
        tfStyle.cursor           = new TextureRegionDrawable(makeCursor());
        tfStyle.background       = new NinePatchDrawable(makeTextFieldBg());
        sk.add("win95-textfield", tfStyle);
    }

    protected NinePatch makeWin95Frame() {
        int S = 64, B = 6;
        Pixmap pm = new Pixmap(S, S, Format.RGBA8888);
        pm.setColor(WIN95_FACE);       pm.fill();
        pm.setColor(WIN95_HIGHLIGHT);  pm.drawLine(0, 0, S - 1, 0); pm.drawLine(0, 1, 0, S - 1);
        pm.setColor(WIN95_DARKSHADOW); pm.drawLine(0, S - 1, S - 1, S - 1); pm.drawLine(S - 1, 0, S - 1, S - 1);
        pm.setColor(WIN95_SHADOW);     pm.drawRectangle(1, 1, S - 2, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        pm.dispose();
        return np;
    }

    protected NinePatch makeBtnBg(boolean pressed) {
        int S = 32, B = 4;
        Pixmap pm = new Pixmap(S, S, Format.RGBA8888);
        pm.setColor(WIN95_FACE); pm.fill();
        pm.setColor(pressed ? WIN95_SHADOW : WIN95_HIGHLIGHT);
        pm.drawLine(0, 0, S - 2, 0); pm.drawLine(0, 1, 0, S - 2);
        pm.setColor(WIN95_DARKSHADOW); pm.drawLine(0, S - 1, S - 1, S - 1); pm.drawLine(S - 1, 0, S - 1, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        pm.dispose();
        return np;
    }

    protected NinePatch makeTextFieldBg() {
        int W = 200, H = 24, B = 4;
        Pixmap pm = new Pixmap(W, H, Format.RGBA8888);
        pm.setColor(WIN95_FACE); pm.fill();
        pm.setColor(WIN95_HIGHLIGHT); pm.drawLine(0, 0, W - 1, 0); pm.drawLine(0, 1, 0, H - 1);
        pm.setColor(WIN95_DARKSHADOW); pm.drawLine(0, H - 1, W - 1, H - 1); pm.drawLine(W - 1, 0, W - 1, H - 1);
        pm.setColor(WIN95_SHADOW); pm.drawRectangle(1, 1, W - 2, H - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        pm.dispose();
        return np;
    }

    protected Texture makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 30, Format.RGBA8888);
        pm.setColor(WIN95_TITLE);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    protected Texture makeCursor() {
        Pixmap pm = new Pixmap(2, 16, Format.RGBA8888);
        pm.setColor(WIN95_TEXT);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }
}

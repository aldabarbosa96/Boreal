package com.boreal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.boreal.assets.GameAssets;
import com.boreal.model.PrimaryStats;
import com.boreal.ui.screens.JobScreen;
import com.boreal.ui.screens.NameScreen;
import com.boreal.ui.screens.StatsScreen;

public final class MainGame extends ApplicationAdapter {

    private Skin skin;
    private Screen screen;
    private GameAssets assets;

    @Override
    public void create() {
        // Carga de assets y skin como antes
        GameAssets.queue();
        GameAssets.finishLoading();
        assets = new GameAssets();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Arrancamos pidiendo el nombre
        setScreen(new NameScreen(skin, name -> {
            // Una vez entrado el nombre, creamos las stats y lanzamos StatsScreen
            PrimaryStats stats = new PrimaryStats();
            StatsScreen statsScreen = new StatsScreen(skin, stats, () -> setScreen(new JobScreen()), name);
            setScreen(statsScreen);
        }));
    }


    private void setScreen(Screen newScreen) {
        if (screen != null) screen.dispose();
        screen = newScreen;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int w, int h) {
        screen.resize(w, h);
    }

    @Override
    public void dispose() {
        screen.dispose();
        skin.dispose();
        assets.dispose();
    }
}

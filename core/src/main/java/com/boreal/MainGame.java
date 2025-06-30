package com.boreal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.boreal.assets.GameAssets;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.ui.screens._1NameScreen;
import com.boreal.ui.screens._2StatsScreen;
import com.boreal.ui.screens._3ProfessionScreen;

import java.util.Map;

public final class MainGame extends ApplicationAdapter {

    private Skin skin;
    private Screen screen;
    private GameAssets assets;

    @Override
    public void create() {
        // 1) Carga de assets y skin
        GameAssets.queue();
        GameAssets.finishLoading();
        assets = new GameAssets();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // 2) Arrancamos pidiendo el nombre
        // dentro de create():
        setScreen(new _1NameScreen(skin, name -> {
            final PrimaryStats stats = new PrimaryStats();
            _2StatsScreen statsScreen = new _2StatsScreen(skin, stats, () -> {
                _3ProfessionScreen profScreen = new _3ProfessionScreen(skin, stats, name, professionList -> {
                    // Aplica bonuses de cada profesión
                    for (Professions.Type prof : professionList) {
                        Map<PrimaryStats.Stat, Integer> bonuses = Professions.getModifiersFor(prof);
                        for (Map.Entry<PrimaryStats.Stat, Integer> entry : bonuses.entrySet()) {
                            for (int i = 0; i < entry.getValue(); i++) {
                                stats.raise(entry.getKey());
                            }
                        }
                    }
                    // todo --> siguiente ventana HABILIDADES
                });
                setScreen(profScreen);
            }, name);
            setScreen(statsScreen);
        }));
    }

    private void setScreen(Screen newScreen) {
        // dispose anterior
        if (screen != null) screen.dispose();
        screen = newScreen;
        // ¡invocamos show() para que Win95Screen.buildContent(...) corra!
        screen.show();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int w, int h) {
        if (screen != null) screen.resize(w, h);
    }

    @Override
    public void dispose() {
        if (screen != null) screen.dispose();
        if (skin != null) skin.dispose();
        if (assets != null) assets.dispose();
    }
}

package com.boreal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.boreal.assets.AssetManager;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.ui.screens._0NameScreen;
import com.boreal.ui.screens._1StatsScreen;
import com.boreal.ui.screens._2ProfessionScreen;

import java.util.Map;

public final class MainGame extends ApplicationAdapter {

    private Skin skin;
    private Screen screen;
    private AssetManager assets;

    @Override
    public void create() {
        // 1) Carga de assets y skin
        AssetManager.queue();
        AssetManager.finishLoading();
        assets = new AssetManager();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // 2) Arrancamos pidiendo el nombre
        setScreen(new _0NameScreen(skin, name -> {
            // 3) Tras introducir nombre, inicializamos stats y mostramos StatsScreen
            final PrimaryStats stats = new PrimaryStats();
            _1StatsScreen statsScreen = new _1StatsScreen(skin, stats,
                // callback al aceptar stats:
                () -> {
                    // 4) Tras aceptar stats, mostramos ProfessionScreen
                    _2ProfessionScreen profScreen = new _2ProfessionScreen(
                        skin,
                        stats,
                        name,
                        newProfessionType -> {
                            // 5) Aplicar bonuses de la profesión a las stats
                            Map<PrimaryStats.Stat, Integer> bonuses =
                                Professions.getModifiersFor(newProfessionType);
                            for (Map.Entry<PrimaryStats.Stat, Integer> entry : bonuses.entrySet()) {
                                PrimaryStats.Stat stat = entry.getKey();
                                int bonusValue = entry.getValue();
                                for (int i = 0; i < bonusValue; i++) {
                                    stats.raise(stat);
                                }
                            }
                            // 6) Aquí podrías seguir al siguiente paso, p.ej.:
                            // setScreen(new GamePlayScreen(skin, stats, name, newProfessionType));
                        });
                    setScreen(profScreen);
                },
                name
            );
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

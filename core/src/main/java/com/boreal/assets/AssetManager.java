package com.boreal.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.boreal.model.Professions;
import com.boreal.ui.screens._2ProfessionScreen;

/**
 * Carga y expone todos los recursos del juego.
 */
public final class AssetManager implements Disposable {

    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    /**
     * Lanza las peticiones de carga (llámalo una sola vez al iniciar).
     */
    public static void queue() {
        // -- iconos de stats --
        String base = "icons/stats/";
        manager.load(base + "strength.png", Texture.class);
        manager.load(base + "agility.png", Texture.class);
        manager.load(base + "endurance.png", Texture.class);
        manager.load(base + "intelligence.png", Texture.class);
        manager.load(base + "perception.png", Texture.class);
        manager.load(base + "charisma.png", Texture.class);
        manager.load(base + "willpower.png", Texture.class);
        manager.load(base + "luck.png", Texture.class);

        // iconos profesiones
        for (Professions.Type t : Professions.Type.values()) {
            manager.load(t.iconPath(), Texture.class);
        }

    }

    public static void finishLoading() {
        manager.finishLoading();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}

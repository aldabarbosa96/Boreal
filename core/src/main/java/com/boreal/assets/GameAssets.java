package com.boreal.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Carga y expone todos los recursos del juego.
 */
public final class GameAssets implements Disposable {

    public static final AssetManager manager = new AssetManager();

    /**
     * Lanza las peticiones de carga (llámalo una sola vez al iniciar).
     */
    public static void queue() {
        // -- iconos de stats --
        String base = "icons/";
        manager.load(base + "strength.png", Texture.class);
        manager.load(base + "agility.png", Texture.class);
        manager.load(base + "endurance.png", Texture.class);
        manager.load(base + "intelligence.png", Texture.class);
        manager.load(base + "perception.png", Texture.class);
        manager.load(base + "charisma.png", Texture.class);
        manager.load(base + "willpower.png", Texture.class);
        manager.load(base + "luck.png", Texture.class);

    }

    public static void finishLoading() {
        manager.finishLoading();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}

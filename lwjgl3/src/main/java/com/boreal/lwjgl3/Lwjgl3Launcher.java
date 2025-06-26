package com.boreal.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.boreal.MainGame;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new MainGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Boreal");

        /* Ventana border-less ligeramente más pequeña (evita cualquier optimización fullscreen). */
        cfg.setDecorated(false);
        cfg.setResizable(false);
        com.badlogic.gdx.Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
        cfg.setWindowedMode(dm.width - 2, dm.height - 2); // 1 px de margen cada lado

        cfg.useVsync(true);
        cfg.setForegroundFPS(dm.refreshRate);

        cfg.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return cfg;
    }

}

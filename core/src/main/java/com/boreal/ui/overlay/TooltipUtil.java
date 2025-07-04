package com.boreal.ui.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class TooltipUtil {

    // Creamos el fondo una sola vez
    private static final NinePatchDrawable BACKGROUND = new NinePatchDrawable(createPaddedPatch());

    private TooltipUtil() { /* no instancias */ }

    /**
     * Asocia un tooltip Win95 al Actor
     */
    public static void attachTooltip(com.badlogic.gdx.scenes.scene2d.Actor target, String text, Skin skin) {
        TextTooltip.TextTooltipStyle style = new TextTooltip.TextTooltipStyle(skin.get("win95-label-black", Label.LabelStyle.class), BACKGROUND);
        TextTooltip tooltip = new TextTooltip(text, style);
        target.addListener(tooltip);
    }

    /**
     * Genera un NinePatch con borde y padding interior
     */
    private static NinePatch createPaddedPatch() {
        // Tamaño razonable para dejar espacio al borde y al padding
        int size = 10;
        Pixmap pm = new Pixmap(size, size, Format.RGBA8888);
        pm.setColor(Color.valueOf("FFFFE0"));   // amarillo pálido
        pm.fill();
        pm.setColor(Color.BLACK);               // borde negro
        pm.drawRectangle(0, 0, size, size);
        Texture tex = new Texture(pm);
        pm.dispose();

        // Definimos el NinePatch: 2px de borde en cada lado
        NinePatch patch = new NinePatch(tex, 2, 2, 2, 2);

        // Ahora le añadimos padding interno (izq, der, arriba, abajo)
        patch.setPadding(6, 6, 4, 4);

        return patch;
    }
}

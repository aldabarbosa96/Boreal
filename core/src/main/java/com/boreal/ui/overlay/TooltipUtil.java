package com.boreal.ui.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Utilidad para crear y adjuntar tooltips al estilo Win95,
 * asumiendo que TooltipManager ya está configurado en MainGame.
 */
public final class TooltipUtil {

    // Fondo amarillo pálido con borde negro
    private static final NinePatchDrawable BACKGROUND = new NinePatchDrawable(createTooltipPatch());

    private TooltipUtil() { /* no instancias */ }

    /**
     * Asocia un tooltip Win95 al Actor.
     *
     * @param target Actor donde hover activará el tooltip
     * @param text   Texto del tooltip
     * @param skin   Skin con estilos (p.ej. "win95-label-black")
     */
    public static void attachTooltip(Actor target, String text, Skin skin) {
        TextTooltip.TextTooltipStyle style = new TextTooltip.TextTooltipStyle(skin.get("win95-label-black", Label.LabelStyle.class), BACKGROUND);
        TextTooltip tooltip = new TextTooltip(text, style);
        target.addListener(tooltip);
    }

    private static NinePatch createTooltipPatch() {
        int size = 10;
        Pixmap pm = new Pixmap(size, size, Format.RGBA8888);
        pm.setColor(Color.valueOf("FFFFE0"));
        pm.fill();
        pm.setColor(Color.BLACK);
        pm.drawRectangle(0, 0, size, size);
        Texture tex = new Texture(pm);
        pm.dispose();
        return new NinePatch(tex, 1, 1, 1, 1);
    }
}

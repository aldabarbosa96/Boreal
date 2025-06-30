package com.boreal.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.boreal.assets.GameAssets.manager;

public final class _3ProfessionScreen extends _0Win95Screen {
    private final PrimaryStats stats;
    private final String playerName;
    private final Consumer<List<Professions.Type>> onAccept;
    private final List<Professions.Type> selected = new ArrayList<>();

    private TextButton acceptBtn;
    private TextButton resetBtn;

    public _3ProfessionScreen(Skin skin, PrimaryStats stats, String playerName, Consumer<List<Professions.Type>> onAccept) {
        super(skin);
        this.stats = stats;
        this.playerName = playerName;
        this.onAccept = onAccept;
    }

    @Override
    public void show() {
        super.show();
        enableEscapeToExit();
        updateButtons();
    }

    @Override
    protected void buildContent(Table win95) {
        // ——— Título —————————————————————
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Jugador: " + playerName + "    Selecciona 5 profesiones", skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).colspan(4).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // ——— Grid de profesiones ———
        Table content = new Table();
        content.defaults().pad(4).uniformX().expandX().fillX();

        for (int i = 0; i < Professions.Type.values().length; i++) {
            Professions.Type t = Professions.Type.values()[i];

            // Icono
            Texture tex = null;
            if (manager.isLoaded(t.iconPath(), Texture.class)) {
                tex = manager.get(t.iconPath(), Texture.class);
                tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }

            // Botón sin texto directo
            TextButton btn = new TextButton("", skin, "win95");
            btn.align(Align.left);

            // Subtabla alineada a la izquierda
            Table inner = new Table();
            inner.align(Align.left);

            // Icono a la izquierda fijo
            if (tex != null) {
                Image icon = new Image(new TextureRegionDrawable(tex));
                icon.setScaling(Scaling.fit);
                inner.add(icon).size(26, 26).padLeft(4).padRight(6);
            }

            // Texto con padding simétrico
            Label lbl = new Label(t.label(), skin, "win95-label-black");
            lbl.setAlignment(Align.left);
            inner.add(lbl).expandX().fillX().padRight(4);

            // Añade la subtabla al botón
            btn.add(inner).expand().fill().left();

            // Listener de selección
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (selected.contains(t)) {
                        selected.remove(t);
                        btn.setColor(Color.WHITE);
                    } else if (selected.size() < 5) {
                        selected.add(t);
                        btn.setColor(Color.LIGHT_GRAY);
                    }
                    updateButtons();
                }
            });

            content.add(btn).height(32);
            if ((i + 1) % 4 == 0) content.row();
        }

        win95.add(content).colspan(4).pad(12).fillX();

        // ——— Fila de Reset / Accept ———
        Table bottom = new Table();
        resetBtn = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");

        resetBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selected.clear();
                content.getChildren().forEach(a -> {
                    if (a instanceof TextButton) ((TextButton) a).setColor(Color.WHITE);
                });
                updateButtons();
            }
        });

        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onAccept.accept(List.copyOf(selected));
            }
        });

        bottom.add(resetBtn).width(120).padRight(8);
        bottom.add(acceptBtn).width(120);
        win95.row();
        win95.add(bottom).colspan(4).center().padBottom(12);
    }

    private void updateButtons() {
        acceptBtn.setDisabled(selected.size() != 5);
    }
}

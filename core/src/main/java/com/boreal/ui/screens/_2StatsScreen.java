package com.boreal.ui.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.model.PrimaryStats;

import java.util.EnumMap;

import static com.boreal.assets.GameAssets.manager;

public final class _2StatsScreen extends _0Win95Screen {
    private final PrimaryStats stats;
    private final Runnable onAccept;
    private final String playerName;

    private Label remainingLabel;
    private TextButton acceptBtn;
    private TextButton resetBtn;
    private final EnumMap<PrimaryStats.Stat, Label> valueLabels = new EnumMap<>(PrimaryStats.Stat.class);

    public _2StatsScreen(Skin skin, PrimaryStats stats, Runnable onAccept, String playerName) {
        super(skin);
        this.stats = stats;
        this.onAccept = onAccept;
        this.playerName = playerName;
        enableEnterSubmit();
        enableEscapeToExit();
    }

    @Override
    public void show() {
        super.show();
        // Después de buildContent(...):
        addEnterKeySupport();
        refreshControls();
    }

    @Override
    protected void buildContent(Table win95) {
        // ── Header ─────────────────────────────────────────────────
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Player: " + playerName, skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).colspan(5).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // ── Stats grid ─────────────────────────────────────────────
        Table content = new Table();
        content.defaults().pad(8);

        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
            // Icono
            Image iconImg = new Image(new TextureRegionDrawable(
                manager.get("icons/stats/" + s.name().toLowerCase() + ".png", Texture.class)));

            // Etiquetas
            Label nameLbl  = new Label(s.label(), skin, "win95-label-black");
            Label valueLbl = new Label(String.valueOf(stats.get(s)), skin, "win95-label-blue");
            valueLabels.put(s, valueLbl);

            // Botones ±
            TextButton minus = new TextButton("-", skin, "win95");
            TextButton plus  = new TextButton("+", skin, "win95");

            ChangeListener refresher = new ChangeListener() {
                @Override public void changed(ChangeEvent e, Actor a) { refreshControls(); }
            };

            plus.addListener(new ChangeListener() {
                @Override public void changed(ChangeEvent e, Actor a) {
                    if (stats.raise(s)) {
                        valueLbl.setText(String.valueOf(stats.get(s)));
                        refresher.changed(e, a);
                    }
                }
            });

            minus.addListener(new ChangeListener() {
                @Override public void changed(ChangeEvent e, Actor a) {
                    if (stats.lower(s)) {
                        valueLbl.setText(String.valueOf(stats.get(s)));
                        refresher.changed(e, a);
                    }
                }
            });

            // Composición de fila
            content.add(iconImg).size(26, 26).padRight(6);
            content.add(nameLbl).left().width(120);
            content.add(valueLbl).center().width(50);
            content.add(minus).size(32, 32);
            content.add(plus).size(32, 32);
            content.row();
        }

        // Label de puntos restantes
        remainingLabel = new Label("", skin, "win95-label-black");
        content.row().padTop(16);
        content.add(remainingLabel).colspan(5).center();

        // Botones Reset / Accept
        content.row().padTop(24).padBottom(12);
        resetBtn = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");

        resetBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                stats.reset();
                for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
                    valueLabels.get(s).setText(String.valueOf(stats.get(s)));
                }
                refreshControls();
            }
        });

        acceptBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                onAccept.run();
            }
        });

        Table btnRow = new Table();
        btnRow.add(resetBtn).width(120).padRight(8);
        btnRow.add(acceptBtn).width(120);
        content.add(btnRow).colspan(5).center();

        // Añadir contenido al frame Win95
        win95.row();
        win95.add(content)
            .colspan(5)
            .padLeft(12)
            .padRight(12)
            .padBottom(12)
            .fillX();
    }

    /** Actualiza el texto y el estado de Accept. */
    private void refreshControls() {
        remainingLabel.setText("Remaining Points: " + stats.getRemainingPoints());
        boolean ready = stats.getRemainingPoints() == 0;
        acceptBtn.setDisabled(!ready);
    }

    /** Soporta pulsar ENTER para aceptar. */
    private void addEnterKeySupport() {
        stage.addListener(new InputListener() {
            @Override public boolean keyDown(InputEvent e, int keycode) {
                if (keycode == Input.Keys.ENTER && !acceptBtn.isDisabled()) {
                    acceptBtn.fire(new ChangeListener.ChangeEvent());
                    return true;
                }
                return false;
            }
        });
    }
}

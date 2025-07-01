package com.boreal.ui.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.model.PrimaryStats;
import com.boreal.ui.overlay.TooltipUtil;

import java.util.EnumMap;
import java.util.Map;

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
        hud.setPlayerName(playerName);
        hud.setStats(Map.of());
        hud.setProfessions(java.util.List.of());
        hud.setHabilities(java.util.List.of());
        addEnterKeySupport();
        refreshControls();
    }

    @Override
    protected void buildContent(Table win95) {
        // Cabecera
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Distribute your stat points", skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).colspan(5).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // Contenedor principal de stats
        Table content = new Table();
        content.defaults().pad(8);

        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
            // 1) Crea icono, etiqueta y valor
            Image iconImg = new Image(new TextureRegionDrawable(
                manager.get("icons/stats/" + s.name().toLowerCase() + ".png", Texture.class)
            ));
            Label nameLbl = new Label(s.label(), skin, "win95-label-black");
            Label valueLbl = new Label(String.valueOf(stats.get(s)), skin, "win95-label-blue");
            valueLabels.put(s, valueLbl);

            TextButton minus = new TextButton("-", skin, "win95");
            TextButton plus  = new TextButton("+", skin, "win95");

            // 2) Construye un contenedor que engloba icono+nombre y reciba el hover:
            Table statHitbox = new Table();
            statHitbox.defaults().left().padRight(4);
            statHitbox.add(iconImg).size(26, 26);
            statHitbox.add(nameLbl).width(120);
            statHitbox.pack();
            statHitbox.setTouchable(Touchable.enabled);

            // 3) Asocia tooltip al hitbox (no al icono ni label por separado)
            String tooltipText = String.format(
                "%s: %d\n\n%s",
                s.label(), stats.get(s), getStatDescription(s)
            );
            TooltipUtil.attachTooltip(statHitbox, tooltipText, skin);

            // 4) Lo añadimos al layout junto al valor y botones
            content.add(statHitbox);
            content.add(valueLbl).center().width(50);
            content.add(minus).size(32, 32);
            content.add(plus).size(32, 32);
            content.row();

            // 5) Lógica de refresco al pulsar +/-:
            ChangeListener refresher = new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    refreshControls();
                }
            };
            plus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.raise(s)) {
                        valueLbl.setText(String.valueOf(stats.get(s)));
                        // actualizar texto del tooltip
                        TooltipUtil.attachTooltip(statHitbox,
                            String.format("%s: %d\n\n%s", s.label(), stats.get(s), getStatDescription(s)),
                            skin
                        );
                        refresher.changed(e, a);
                    }
                }
            });
            minus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.lower(s)) {
                        valueLbl.setText(String.valueOf(stats.get(s)));
                        TooltipUtil.attachTooltip(statHitbox,
                            String.format("%s: %d\n\n%s", s.label(), stats.get(s), getStatDescription(s)),
                            skin
                        );
                        refresher.changed(e, a);
                    }
                }
            });
        }

        // Puntos restantes
        remainingLabel = new Label("", skin, "win95-label-black");
        content.row().padTop(16);
        content.add(remainingLabel).colspan(5).center();

        // Reset / Accept
        content.row().padTop(24).padBottom(12);
        resetBtn  = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");
        resetBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                stats.reset();
                for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {
                    valueLabels.get(s).setText(String.valueOf(stats.get(s)));
                }
                refreshControls();
            }
        });
        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                hud.setStats(stats.asMap());
                onAccept.run();
            }
        });
        Table btnRow = new Table();
        btnRow.add(resetBtn).width(120).padRight(8);
        btnRow.add(acceptBtn).width(120);
        content.add(btnRow).colspan(5).center();

        // Insertar contenido
        win95.row();
        win95.add(content).colspan(5).padLeft(12).padRight(12).padBottom(12).fillX();
    }

    private void refreshControls() {
        remainingLabel.setText("Remaining Points: " + stats.getRemainingPoints());
        acceptBtn.setDisabled(stats.getRemainingPoints() != 0);
    }

    private void addEnterKeySupport() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent e, int keycode) {
                if (keycode == Input.Keys.ENTER && !acceptBtn.isDisabled()) {
                    acceptBtn.fire(new ChangeListener.ChangeEvent());
                    return true;
                }
                return false;
            }
        });
    }

    private String getStatDescription(PrimaryStats.Stat s) {
        switch (s) {
            case STRENGTH:
                return "Aumenta el daño cuerpo a cuerpo\nAumenta el peso que puedes cargar.";
            case AGILITY:
                return "Mejora la velocidad de ataque\nAumenta la probabilidad de evasión.";
            case ENDURANCE:
                return "Incrementa la salud máxima\nMejora la resistencia al daño.";
            case INTELLIGENCE:
                return "Aumenta la experiencia ganada\nEficacia en habilidades técnicas.";
            case PERCEPTION:
                return "Mejora la puntería y precisión\nAumenta la detección de peligros.";
            case CHARISMA:
                return "Mejora las opciones de compra/venta\nAumenta las opciones de diálogo.";
            case WILLPOWER:
                return "Reduce el coste de habilidades especiales\ny resistencia al estrés.";
            case LUCK:
                return "Aumenta la probabilidad de críticos\ny hallazgos raros.";
            default:
                return "";
        }
    }
}

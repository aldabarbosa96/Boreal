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
import com.boreal.model.DerivedAttributes;
import com.boreal.model.PrimaryStats;
import com.boreal.ui.overlay.TooltipData;
import com.boreal.ui.overlay.TooltipUtil;

import java.util.EnumMap;
import java.util.Map;

import static com.boreal.assets.GameAssets.manager;

public final class _2StatsScreen extends _0Win95Screen {

    private final PrimaryStats stats;
    private final DerivedAttributes derived = new DerivedAttributes();
    private final Runnable onAccept;
    private final String playerName;

    /* UI */
    private Label remainingLabel;
    private TextButton acceptBtn, resetBtn;
    private final EnumMap<PrimaryStats.Stat, Label> valueLabels = new EnumMap<>(PrimaryStats.Stat.class);
    private final EnumMap<DerivedAttributes.Attr, Label> derivedLabels = new EnumMap<>(DerivedAttributes.Attr.class);

    public _2StatsScreen(Skin skin, PrimaryStats stats, Runnable onAccept, String playerName) {
        super(skin);
        this.stats = stats;
        this.onAccept = onAccept;
        this.playerName = playerName;

        derived.recalc(stats);
        enableEnterSubmit();
        enableEscapeToExit();
    }

    /*────────────────────────────── ciclo ──────────────────────────────*/

    @Override
    public void show() {
        super.show();

        hud.setPlayerName(playerName);
        hud.setStatsAndDerived(Map.of(), Map.of());
        hud.setProfessionsAndSkills(java.util.List.of(), java.util.List.of());

        addEnterKeySupport();
        refreshControls();
        updateDerivedLabels();
    }

    /*────────────────────────────── UI ──────────────────────────────*/

    @Override
    protected void buildContent(Table win95) {

        /*— cabecera —*/
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Distribute your stat points", skin, "win95-title-label");
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);

        win95.add(header).colspan(2).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        /* columnas (primarias / derivadas) */
        Table leftCol = new Table();
        leftCol.defaults().pad(4);
        Table rightCol = new Table();
        rightCol.defaults().pad(4);
        leftCol.setWidth(340);
        rightCol.setWidth(300);

        /*──────── PRIMARY STATS — izquierda ────────*/
        for (PrimaryStats.Stat s : PrimaryStats.Stat.values()) {

            Image icon = new Image(new TextureRegionDrawable(manager.get("icons/stats/" + s.name().toLowerCase() + ".png", Texture.class)));

            Label name = new Label(s.label(), skin, "win95-label-black");
            Label value = new Label(String.valueOf(stats.get(s)), skin, "win95-label-blue");
            valueLabels.put(s, value);
            value.setAlignment(Align.center);

            TextButton minus = new TextButton("-", skin, "win95");
            TextButton plus = new TextButton("+", skin, "win95");

            Table hitbox = new Table();
            hitbox.defaults().left().padRight(4);
            hitbox.add(icon).size(26, 26);
            hitbox.add(name).width(120);
            hitbox.pack();
            hitbox.setTouchable(Touchable.enabled);
            TooltipUtil.attachTooltip(hitbox, TooltipData.statTooltip(s, stats.get(s)), skin);

            leftCol.add(hitbox);
            leftCol.add(value).width(50);
            leftCol.add(minus).size(32, 32);
            leftCol.add(plus).size(32, 32);
            leftCol.row();

            /* listeners + / - */
            ChangeListener refreshAll = new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    derived.recalc(stats);
                    updateDerivedLabels();
                    refreshControls();
                }
            };

            plus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.raise(s)) {
                        value.setText(String.valueOf(stats.get(s)));
                        TooltipUtil.attachTooltip(hitbox, TooltipData.statTooltip(s, stats.get(s)), skin);
                        refreshAll.changed(e, a);
                    }
                }
            });
            minus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (stats.lower(s)) {
                        value.setText(String.valueOf(stats.get(s)));
                        TooltipUtil.attachTooltip(hitbox, TooltipData.statTooltip(s, stats.get(s)), skin);
                        refreshAll.changed(e, a);
                    }
                }
            });
        }

        /*──────── DERIVED — derecha ────────*/
        rightCol.columnDefaults(0).left().width(220);
        rightCol.columnDefaults(1).right().width(60);

        Label derTitle = new Label("Derived Attributes", skin, "win95-label-black");
        rightCol.add(derTitle).left().colspan(2).row();

        for (DerivedAttributes.Attr a : DerivedAttributes.Attr.values()) {
            Label name = new Label(formatAttrName(a), skin, "win95-label-black");
            TooltipUtil.attachTooltip(name, TooltipData.attrTooltip(a), skin);

            Label val = new Label(formatAttrValue(a), skin, "win95-label-blue");
            val.setAlignment(Align.right);
            derivedLabels.put(a, val);

            rightCol.add(name).left();
            rightCol.add(val).right();
            rightCol.row();
        }

        /*──────── controles inferiores — izquierda ─────────*/
        remainingLabel = new Label("", skin, "win95-label-black");
        leftCol.row().padTop(16);
        leftCol.add(remainingLabel).colspan(4).center();

        leftCol.row().padTop(24).padBottom(12);
        resetBtn = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");

        resetBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                stats.reset();
                stats.asMap().forEach((st, v) -> valueLabels.get(st).setText(String.valueOf(v)));
                derived.recalc(stats);
                updateDerivedLabels();
                refreshControls();
            }
        });

        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                hud.setStatsAndDerived(stats.asMap(), derived.asMap());
                hud.setProfessionsAndSkills(java.util.List.of(), java.util.List.of());
                onAccept.run();
            }
        });

        Table btnRow = new Table();
        btnRow.add(resetBtn).width(120).padRight(8);
        btnRow.add(acceptBtn).width(120);
        leftCol.add(btnRow).colspan(4).center();

        /*──────── pack final ─────────*/
        Table content = new Table();
        content.defaults().pad(8).top();
        content.add(leftCol).width(340).top().padRight(24);
        content.add(rightCol).width(300).top();

        win95.row();
        win95.add(content).colspan(2).pad(12, 12, 12, 12).fillX();
    }

    /*──────────────────────── helpers ───────────────────────*/
    private String formatAttrName(DerivedAttributes.Attr a) {
        return a.name().replace('_', ' ');
    }

    private String formatAttrValue(DerivedAttributes.Attr a) {
        float v = derived.get(a);
        return (v % 1 == 0) ? String.valueOf((int) v) : String.format("%.1f", v);
    }

    private void updateDerivedLabels() {
        derivedLabels.forEach((a, l) -> l.setText(formatAttrValue(a)));
    }

    private void refreshControls() {
        remainingLabel.setText("Remaining Points: " + stats.getRemainingPoints());
        acceptBtn.setDisabled(stats.getRemainingPoints() != 0);
    }

    private void addEnterKeySupport() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent e, int key) {
                if (key == Input.Keys.ENTER && !acceptBtn.isDisabled()) {
                    acceptBtn.fire(new ChangeListener.ChangeEvent());
                    return true;
                }
                return false;
            }
        });
    }
}

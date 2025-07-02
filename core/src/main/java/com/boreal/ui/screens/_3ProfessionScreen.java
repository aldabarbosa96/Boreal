package com.boreal.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.model.*;
import com.boreal.model.Skills.Skill;
import com.boreal.ui.overlay.TooltipData;
import com.boreal.ui.overlay.TooltipUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.boreal.assets.GameAssets.manager;

public final class _3ProfessionScreen extends _0Win95Screen {

    private static final int MAX_SELECTIONS = 5;

    private final PrimaryStats stats;
    private final DerivedAttributes derived = new DerivedAttributes();
    private final Skills skills;
    private final String playerName;
    private final Consumer<List<Professions.Type>> onAccept;

    private final List<Professions.Type> selected = new ArrayList<>();

    private TextButton acceptBtn, resetBtn;
    private Label countLabel;

    public _3ProfessionScreen(Skin skin, PrimaryStats stats, Skills skills, String playerName, Consumer<List<Professions.Type>> onAccept) {
        super(skin);
        this.stats = stats;
        this.skills = skills;
        this.playerName = playerName;
        this.onAccept = onAccept;

        derived.recalc(stats);
        enableEscapeToExit();
    }

    /*──────────────────────────── ciclo ──────────────────────────────*/

    @Override
    public void show() {
        super.show();
        hud.setPlayerName(playerName);
        hud.setStatsAndDerived(stats.asMap(), derived.asMap());
        hud.setProfessionsAndSkills(selected, List.of());
        updateButtons();
    }

    /*──────────────────────────── UI ──────────────────────────────*/

    @Override
    protected void buildContent(Table win95) {

        /*— cabecera —*/
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label nameL = new Label("Player: " + playerName, skin, "win95-title-label");
        nameL.setAlignment(Align.left);
        countLabel = new Label("", skin, "win95-title-label");
        countLabel.setAlignment(Align.right);
        header.add(nameL).left().expandX().pad(4, 6, 4, 6);
        header.add(countLabel).right().pad(4, 6, 4, 6);

        win95.add(header).colspan(4).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        /*— grid de profesiones —*/
        Table grid = new Table();
        grid.defaults().pad(4).uniformX().expandX().fillX();

        Professions.Type[] types = Professions.Type.values();
        for (int i = 0; i < types.length; i++) {

            Professions.Type t = types[i];
            Texture tex = manager.isLoaded(t.iconPath(), Texture.class) ? manager.get(t.iconPath(), Texture.class) : null;

            TextButton btn = new TextButton(t.label(), skin, "win95");
            btn.align(Align.left);
            if (tex != null) {
                tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                btn.getLabelCell().padLeft(4);
                btn.add(new Image(new TextureRegionDrawable(tex))).size(26, 26).padRight(4);
            }

            TooltipUtil.attachTooltip(btn, TooltipData.professionTooltip(t), skin);

            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent e, Actor a) {
                    if (selected.contains(t)) {
                        selected.remove(t);
                        btn.setColor(Color.WHITE);
                    } else if (selected.size() < MAX_SELECTIONS) {
                        selected.add(t);
                        btn.setColor(Color.LIGHT_GRAY);
                    }
                    updateButtons();
                }
            });

            grid.add(btn).height(32);
            if ((i + 1) % 4 == 0) grid.row();
        }

        win95.add(grid).colspan(4).pad(12).fillX();

        /*— botones —*/
        Table bottom = new Table();
        resetBtn = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");

        resetBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                selected.clear();
                grid.getChildren().forEach(w -> {
                    if (w instanceof TextButton) w.setColor(Color.WHITE);
                });
                updateButtons();
            }
        });

        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {
                onAccept.accept(new ArrayList<>(selected));

                List<Skill> allSkills = selected.stream().flatMap(p -> DerivedSkills.getDerivedSkills(p).stream()).distinct().toList();

                hud.setProfessionsAndSkills(selected, allSkills);
            }
        });

        bottom.add(resetBtn).width(120).padRight(8);
        bottom.add(acceptBtn).width(120);

        win95.row();
        win95.add(bottom).colspan(4).center().padBottom(12);
    }

    /*──────────────────────── helpers ───────────────────────*/
    private void updateButtons() {
        int rem = MAX_SELECTIONS - selected.size();
        countLabel.setText(rem + " profession" + (rem == 1 ? "" : "s") + " remaining");

        hud.setStatsAndDerived(stats.asMap(), derived.asMap());

        List<Skill> currentSkills = selected.stream().flatMap(p -> DerivedSkills.getDerivedSkills(p).stream()).distinct().toList();
        hud.setProfessionsAndSkills(selected, currentSkills);

        acceptBtn.setDisabled(selected.size() != MAX_SELECTIONS);
    }
}

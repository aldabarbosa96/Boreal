package com.boreal.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.model.PrimaryStats;
import com.boreal.model.Professions;
import com.boreal.ui.overlay.TooltipUtil;
import com.boreal.util.DerivedSkillsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.boreal.assets.GameAssets.manager;

public final class _3ProfessionScreen extends _0Win95Screen {
    private static final int MAX_SELECTIONS = 5;

    private final PrimaryStats stats;
    private final String playerName;
    private final Consumer<List<Professions.Type>> onAccept;
    private final List<Professions.Type> selected = new ArrayList<>();

    private TextButton acceptBtn;
    private TextButton resetBtn;
    private Label countLabel;

    public _3ProfessionScreen(Skin skin, PrimaryStats stats, String playerName, Consumer<List<Professions.Type>> onAccept) {
        super(skin);
        this.stats = stats;
        this.playerName = playerName;
        this.onAccept = onAccept;
        enableEscapeToExit();
    }

    @Override
    protected void buildContent(Table win95) {
        // Header
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label nameLabel = new Label("Player: " + playerName, skin, "win95-title-label");
        nameLabel.setAlignment(Align.left);
        countLabel = new Label(MAX_SELECTIONS + " professions remaining", skin, "win95-title-label");
        countLabel.setAlignment(Align.right);
        header.add(nameLabel).left().expandX().pad(4, 6, 4, 6);
        header.add(countLabel).right().pad(4, 6, 4, 6);
        win95.add(header).colspan(4).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // Professions grid
        Table content = new Table();
        content.defaults().pad(4).uniformX().expandX().fillX();
        Professions.Type[] types = Professions.Type.values();
        for (int i = 0; i < types.length; i++) {
            Professions.Type t = types[i];
            Texture tex = null;
            if (manager.isLoaded(t.iconPath(), Texture.class)) {
                tex = manager.get(t.iconPath(), Texture.class);
                tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            }
            TextButton btn = new TextButton(t.label(), skin, "win95");
            btn.align(Align.left);
            // Add icon if available
            if (tex != null) {
                btn.getLabelCell().padLeft(4);
                btn.add(new Image(new TextureRegionDrawable(tex))).size(26, 26).padRight(4);
            }

            // Tooltip: derived skills
            List<String> skills = DerivedSkillsUtil.getDerivedSkills(t);
            String tooltipText = t.label() + " skills:\n" + String.join("\n", skills);
            TooltipUtil.attachTooltip(btn, tooltipText, skin);

            // Selection logic
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
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

            content.add(btn).height(32);
            if ((i + 1) % 4 == 0) content.row();
        }
        win95.add(content).colspan(4).pad(12).fillX();

        // Reset / Accept
        Table bottom = new Table();
        resetBtn = new TextButton("Reset", skin, "win95");
        acceptBtn = new TextButton("Accept", skin, "win95");
        resetBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                selected.clear();
                content.getChildren().forEach(a -> {
                    if (a instanceof TextButton) ((TextButton) a).setColor(Color.WHITE);
                });
                updateButtons();
            }
        });
        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                onAccept.accept(new ArrayList<>(selected));
            }
        });
        bottom.add(resetBtn).width(120).padRight(8);
        bottom.add(acceptBtn).width(120);
        win95.row();
        win95.add(bottom).colspan(4).center().padBottom(12);
    }

    @Override
    public void show() {
        super.show();
        hud.setPlayerName(playerName);
        hud.setStats(stats.asMap());
        hud.setProfessions(selected);
        updateButtons();
    }

    private void updateButtons() {
        acceptBtn.setDisabled(selected.size() != MAX_SELECTIONS);
        int rem = MAX_SELECTIONS - selected.size();
        countLabel.setText(rem + (rem == 1 ? " profession remaining" : " professions remaining"));
        hud.setStats(stats.asMap());
        hud.setProfessions(selected);
    }
}

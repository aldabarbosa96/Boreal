package com.boreal.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class _1NameScreen extends _0Win95Screen {

    private static final int MAX_NAME_LENGTH = 20;
    private final Consumer<String> onNameEntered;

    private TextField nameField;
    private Label errorLabel;
    private TextButton acceptBtn;

    public _1NameScreen(Skin skin, Consumer<String> onNameEntered) {
        super(skin);
        this.onNameEntered = onNameEntered;
    }

    /*────────────────────────────── UI ──────────────────────────────*/

    @Override
    protected void buildContent(Table win95) {

        /*— cabecera —*/
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Enter your name", skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);

        win95.add(header).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        /*— formulario —*/
        Table content = new Table();
        content.defaults().pad(8);

        nameField = new TextField("", skin, "win95-textfield");
        nameField.setMessageText("Type here...");
        nameField.setMaxLength(MAX_NAME_LENGTH);
        content.add(nameField).width(300).row();

        errorLabel = new Label("", skin, "win95-label-blue");
        content.add(errorLabel).row();

        acceptBtn = new TextButton("Accept", skin, "win95");
        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor a) {

                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    errorLabel.setText("Name cannot be empty.");
                    return;
                }
                if (name.length() > MAX_NAME_LENGTH) name = name.substring(0, MAX_NAME_LENGTH);
                nameField.setText(name);

                /* Actualizamos HUD */
                hud.setPlayerName(name);
                hud.setStatsAndDerived(Map.of(), Map.of());
                hud.setProfessionsAndSkills(List.of(), List.of());

                onNameEntered.accept(name);
            }
        });
        content.add(acceptBtn).row();

        win95.add(content).pad(12).fillX();
    }

    @Override
    public void show() {
        super.show();
        enableEnterSubmit();
        enableEscapeToExit();

        /* HUD limpio */
        hud.setPlayerName("");
        hud.setStatsAndDerived(Map.of(), Map.of());
        hud.setProfessionsAndSkills(List.of(), List.of());
    }
}


package com.boreal.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boreal.ui.overlay.HUD;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class _1NameScreen extends _0Win95Screen {
    private final Consumer<String> onNameEntered;
    private TextField nameField;
    private Label errorLabel;
    private TextButton acceptBtn;

    public _1NameScreen(Skin skin, Consumer<String> onNameEntered) {
        super(skin);
        this.onNameEntered = onNameEntered;
    }

    @Override
    protected void buildContent(Table win95) {
        // ——— Cabecera —————————————————————
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Enter your name", skin, "win95-title-label");
        title.setAlignment(Align.left);
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // ——— Formulario de nombre —————————————————
        Table content = new Table();
        content.defaults().pad(8);

        nameField = new TextField("", skin, "win95-textfield");
        nameField.setMessageText("Type here...");
        content.add(nameField).width(300).row();

        errorLabel = new Label("", skin, "win95-label-blue");
        content.add(errorLabel).row();

        acceptBtn = new TextButton("Accept", skin, "win95");
        acceptBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    errorLabel.setText("Name cannot be empty.");
                } else {
                    // Actualizamos el HUD antes de cambiar de pantalla
                    hud.setPlayerName(name);
                    // Limpiamos el resto por ahora
                    hud.setStats(Map.of());
                    hud.setProfessions(List.of());
                    hud.setHabilities(List.of());
                    onNameEntered.accept(name);
                }
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
        // Inicializamos el HUD con valores vacíos
        hud.setPlayerName("");
        hud.setStats(Map.of());
        hud.setProfessions(List.of());
        hud.setHabilities(List.of());
    }
}

// core/src/main/java/com/boreal/ui/screens/_1NameScreen.java
package com.boreal.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.function.Consumer;

public class _1NameScreen extends _0Win95Screen {
    private final Consumer<String> onNameEntered;
    private TextField nameField;
    private Label errorLabel;

    public _1NameScreen(Skin skin, Consumer<String> onNameEntered) {
        super(skin);
        this.onNameEntered = onNameEntered;
    }

    @Override
    public void show() {
        super.show();
        // registramos el ENTER **después** de haber construido la UI:
        enableEnterSubmit();
        enableEscapeToExit();
    }

    @Override
    protected void buildContent(Table win95) {
        // ── Header ─────────────────────────────────────────────────
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label title = new Label("Enter your name", skin, "win95-title-label");
        header.add(title).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).fillX().pad(-2, -2, 2, -2);
        win95.row();

        // ── Content ───────────────────────────────────────────────
        Table content = new Table();
        content.defaults().pad(8);

        nameField = new TextField("", skin, "win95-textfield");
        nameField.setMessageText("Type here...");
        content.add(nameField).width(300).row();

        errorLabel = new Label("", skin, "win95-label-blue");
        content.add(errorLabel).row();

        TextButton nextBtn = new TextButton("Accept", skin, "win95");
        nextBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    errorLabel.setText("Name cannot be empty.");
                } else {
                    onNameEntered.accept(name);
                }
            }
        });
        content.add(nextBtn).row();

        win95.add(content).pad(12).fillX();
    }
}

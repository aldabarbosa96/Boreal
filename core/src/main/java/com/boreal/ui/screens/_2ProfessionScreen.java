package com.boreal.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.boreal.model.PrimaryStats;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Pantalla de selección de oficio (professions) en estilo Win95.
 * Al elegir uno, invoca el callback con el tipo seleccionado.
 */
public final class _2ProfessionScreen extends ScreenAdapter implements Serializable {

    // ───────────────── Win95 system‐palette ─────────────────
    private static final Color WIN95_FACE = Color.valueOf("C0C0C0");
    private static final Color WIN95_HIGHLIGHT = Color.valueOf("FFFFFF");
    private static final Color WIN95_SHADOW = Color.valueOf("808080");
    private static final Color WIN95_DARKSHADOW = Color.valueOf("404040");
    private static final Color WIN95_TEXT = Color.valueOf("000000");
    private static final Color WIN95_TITLE = Color.valueOf("000080");
    // ────────────────────────────────────────────────────────

    private final Stage stage;
    private final Skin skin;
    private final PrimaryStats stats;
    private final String playerName;
    private final Consumer<Type> onProfessionChosen;

    public _2ProfessionScreen(Skin skin, PrimaryStats stats, String playerName, Consumer<Type> onProfessionChosen) {
        this.skin = skin;
        this.stats = stats;
        this.playerName = playerName;
        this.onProfessionChosen = onProfessionChosen;

        ensureWin95Styles(skin);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildUI();
        addEnterKeySupport();
    }

    private void buildUI() {
        // contenedor raíz
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // marco Win95
        Table win95 = new Table();
        win95.setBackground(new NinePatchDrawable(makeWin95Frame()));
        root.add(win95).center();
        win95.row();

        // cabecera
        Table header = new Table();
        header.setBackground(new TextureRegionDrawable(makeTitleBackground()));
        Label headerLabel = new Label("Jugador: " + playerName + "    Elige oficio", skin, "win95-title-label");
        headerLabel.setAlignment(Align.left);
        header.add(headerLabel).left().expandX().fillX().pad(4, 6, 4, 6);
        win95.add(header).colspan(7)       // 7 columnas en total
            .fillX().pad(-2, -2, 2, -2);
        win95.row();
        win95.defaults().pad(8);

        // contenido: botones por oficio
        Table content = new Table();
        content.defaults().pad(8);

        // 1) calcular ancho máximo para los botones usando la fuente registrada "font-win95"
        BitmapFont font = skin.get("font-win95", BitmapFont.class);
        GlyphLayout layout = new GlyphLayout();
        float maxLabelWidth = 0;
        for (Type t : Type.values()) {
            layout.setText(font, t.label());
            maxLabelWidth = Math.max(maxLabelWidth, layout.width);
        }
        // añadir espacio extra para padding interior
        float buttonWidth = maxLabelWidth + 20;

        // 2) añadir botones en 7 columnas
        Type[] types = Type.values();
        for (int i = 0; i < types.length; i++) {
            Type t = types[i];
            TextButton btn = new TextButton(t.label(), skin, "win95");
            btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    onProfessionChosen.accept(t);
                }
            });
            content.add(btn).width(buttonWidth).height(32);
            if ((i + 1) % 7 == 0) {
                content.row();
            }
        }

        win95.add(content).colspan(7)   // coincide con el header
            .pad(12).fillX();
    }


    private void addEnterKeySupport() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // ────────────────────────── Profesiones ──────────────────────────

    public enum Type {
        MECHANIC("Mechanic"), ELECTRICIAN("Electrician"), PLUMBER("Plumber"), CARPENTER("Carpenter"), WELDER("Welder"), MASON("Mason"), BLACKSMITH("Blacksmith"), MAINTENANCE_TECHNICIAN("Maintenance Technician"), SCRAP_DEALER("Scrap Dealer"), LOCKSMITH("Locksmith"), BIKE_REPAIRER("Bike Repairer"), MACHINERY_ASSEMBLER("Machinery Assembler"), ARTISAN_GUNSMITH("Artisanal Gunsmith"), REFRIGERATION_TECHNICIAN("Refrigeration Technician"), SOLAR_TECHNICIAN("Solar Technician"), RADIO_TECHNICIAN("Radio Technician"), SOLDIER("Soldier"), POLICE("Police"), PRIVATE_SECURITY("Private Security"), COMBAT_INSTRUCTOR("Combat Instructor"), EX_MILITARY("Ex-Military"), EX_POLICE("Ex-Police"), MILITIAMAN("Militiaman"), NEIGHBORHOOD_WATCH("Neighborhood Watch"), FILM_SPECIALIST("Film Specialist"), AIRSOFT_PLAYER("Airsoft Player"), SPORT_ARCHER("Sport Archer"), BOUNTY_HUNTER("Bounty Hunter"), PRIVATE_DETECTIVE("Private Detective"), BODYGUARD("Bodyguard"), HUNTER("Hunter"), FISHERMAN("Fisherman"), FARMER("Farmer"), RANCHER("Rancher"), AGRICULTURAL_WORKER("Agricultural Worker"), PLANT_FORAGER("Plant Forager"), FORESTER("Forester"), SCOUT("Scout"), SURVIVALIST("Survivalist"), NOMADIC_TRAVELER("Nomadic Traveler"), WANDERER("Wanderer"), CAMP_COUNSELOR("Camp Counselor"), PROFESSIONAL_CLIMBER("Professional Climber"), SURVIVAL_INSTRUCTOR("Survival Instructor"), MOUNTAIN_RESCUER("Mountain Rescuer"), FERRYMAN("Ferryman"), BOATMAN("Boatman"), MEDIC("Medic"), SURGEON("Surgeon"), NURSE("Nurse"), PARAMEDIC("Paramedic"), VETERINARIAN("Veterinarian"), PHARMACIST("Pharmacist"), HERBALIST("Herbalist"), SELF_TAUGHT_HERBALIST("Self-Taught Herbalist"), PSYCHOLOGIST("Psychologist"), THERAPIST("Therapist"), REFORMED_ALCOHOLIC("Reformed Alcoholic"), COOK("Cook"), BUTCHER("Butcher"), BAKER("Baker"), CHEESEMAKER("Cheesemaker"), URBAN_FARMER("Urban Farmer"), CANNABIS_GROWER("Cannabis Grower"), GARDENER("Gardener"), BEEKEEPER("Beekeeper"), TRUCK_DRIVER("Truck Driver"), BUS_DRIVER("Bus Driver"), PILOT("Pilot"), SAILOR("Sailor"), BIKER("Biker"), DELIVERY_DRIVER("Delivery Driver"), FAIRGROUND_WORKER("Fairground Worker"), SCIENTIST("Scientist"), ENGINEER("Engineer"), BIOLOGIST("Biologist"), CHEMIST("Chemist"), PROGRAMMER("Programmer"), LAB_TECHNICIAN("Lab Technician"), HACKER("Hacker"), MINER("Miner"), SURVEYOR("Surveyor"), RADIO_BROADCASTER("Radio Broadcaster"), ORNITHOLOGIST("Ornithologist"), TEACHER("Teacher"), LIBRARIAN("Librarian"), JOURNALIST("Journalist"), WAR_CORRESPONDENT("War Correspondent"), STUDENT("Student"), RELIGIOUS_LEADER("Religious Leader"), SOCIAL_WORKER("Social Worker"), ACTOR("Actor"), STREET_PERFORMER("Street Performer"), SCULPTOR("Sculptor"), MUSICIAN("Musician"), STAGEHAND("Stagehand"), HOMEMADE_WEAPONS_DESIGNER("Homemade Weapons Designer"), THIEF("Thief"), SMALL_TIME_CRIMINAL("Small-Time Criminal"), ESCAPED_CONVICT("Escaped Convict"), PICKPOCKET("Pickpocket"), SMUGGLER("Smuggler"), FIREFIGHTER("Firefighter"), GRAVEDIGGER("Gravedigger"), GAS_STATION_ATTENDANT("Gas Station Attendant"), SUPERMARKET_MANAGER("Supermarket Manager"), GARBAGE_COLLECTOR("Garbage Collector"), CLEANER("Cleaner"), OCCULTIST("Occultist"), MEDIUM("Medium"), EXILE_OR_DESERTER("Exile/Deserter"), CULT_LEADER("Cult Leader");
        private final String label;

        Type(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    private static final Map<Type, EnumMap<PrimaryStats.Stat, Integer>> MODIFIERS = Map.ofEntries(Map.entry(Type.MECHANIC, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.AGILITY, 3, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.ELECTRICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.PLUMBER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.CARPENTER, modifiers(PrimaryStats.Stat.STRENGTH, 4, PrimaryStats.Stat.AGILITY, 3)), Map.entry(Type.WELDER, modifiers(PrimaryStats.Stat.STRENGTH, 5, PrimaryStats.Stat.ENDURANCE, 3)), Map.entry(Type.MASON, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 3)), Map.entry(Type.BLACKSMITH, modifiers(PrimaryStats.Stat.STRENGTH, 6, PrimaryStats.Stat.ENDURANCE, 4)), Map.entry(Type.MAINTENANCE_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.SCRAP_DEALER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.LOCKSMITH, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.BIKE_REPAIRER, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.INTELLIGENCE, 3)), Map.entry(Type.MACHINERY_ASSEMBLER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.ARTISAN_GUNSMITH, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.REFRIGERATION_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.SOLAR_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.RADIO_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.SOLDIER, modifiers(PrimaryStats.Stat.STRENGTH, 6, PrimaryStats.Stat.ENDURANCE, 5)), Map.entry(Type.POLICE, modifiers(PrimaryStats.Stat.STRENGTH, 5, PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.CHARISMA, 1)), Map.entry(Type.PRIVATE_SECURITY, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.COMBAT_INSTRUCTOR, modifiers(PrimaryStats.Stat.STRENGTH, 5, PrimaryStats.Stat.AGILITY, 4)), Map.entry(Type.EX_MILITARY, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.EX_POLICE, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.MILITIAMAN, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.STRENGTH, 3)), Map.entry(Type.NEIGHBORHOOD_WATCH, modifiers(PrimaryStats.Stat.PERCEPTION, 3, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.FILM_SPECIALIST, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.INTELLIGENCE, 3)), Map.entry(Type.AIRSOFT_PLAYER, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.SPORT_ARCHER, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.BOUNTY_HUNTER, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.PRIVATE_DETECTIVE, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.INTELLIGENCE, 3)), Map.entry(Type.BODYGUARD, modifiers(PrimaryStats.Stat.STRENGTH, 5, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.HUNTER, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.FISHERMAN, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.FARMER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.RANCHER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.AGRICULTURAL_WORKER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.PLANT_FORAGER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.FORESTER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.SCOUT, modifiers(PrimaryStats.Stat.AGILITY, 6, PrimaryStats.Stat.PERCEPTION, 4)), Map.entry(Type.SURVIVALIST, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.INTELLIGENCE, 3)), Map.entry(Type.NOMADIC_TRAVELER, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.WANDERER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.CAMP_COUNSELOR, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.PROFESSIONAL_CLIMBER, modifiers(PrimaryStats.Stat.AGILITY, 6, PrimaryStats.Stat.ENDURANCE, 3)), Map.entry(Type.SURVIVAL_INSTRUCTOR, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.MOUNTAIN_RESCUER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.FERRYMAN, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.BOATMAN, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.MEDIC, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.SURGEON, modifiers(PrimaryStats.Stat.INTELLIGENCE, 7, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.NURSE, modifiers(PrimaryStats.Stat.CHARISMA, 3, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.PARAMEDIC, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.VETERINARIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.PHARMACIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.HERBALIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.SELF_TAUGHT_HERBALIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.LUCK, 1)), Map.entry(Type.PSYCHOLOGIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.CHARISMA, 3)), Map.entry(Type.THERAPIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.CHARISMA, 3)), Map.entry(Type.REFORMED_ALCOHOLIC, modifiers(PrimaryStats.Stat.WILLPOWER, 5, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.COOK, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.BUTCHER, modifiers(PrimaryStats.Stat.STRENGTH, 4, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.BAKER, modifiers(PrimaryStats.Stat.ENDURANCE, 3, PrimaryStats.Stat.INTELLIGENCE, 2)), Map.entry(Type.CHEESEMAKER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.LUCK, 1)), Map.entry(Type.URBAN_FARMER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.CANNABIS_GROWER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.GARDENER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.ENDURANCE, 2)), Map.entry(Type.BEEKEEPER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.TRUCK_DRIVER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.BUS_DRIVER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.PILOT, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.SAILOR, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.BIKER, modifiers(PrimaryStats.Stat.AGILITY, 5, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.DELIVERY_DRIVER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.FAIRGROUND_WORKER, modifiers(PrimaryStats.Stat.ENDURANCE, 3, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.SCIENTIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 7, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.ENGINEER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.BIOLOGIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.CHEMIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.PROGRAMMER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 7, PrimaryStats.Stat.WILLPOWER, 2)), Map.entry(Type.LAB_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.HACKER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 3)), Map.entry(Type.MINER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.SURVEYOR, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.INTELLIGENCE, 2)), Map.entry(Type.RADIO_BROADCASTER, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.ORNITHOLOGIST, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.INTELLIGENCE, 2)), Map.entry(Type.TEACHER, modifiers(PrimaryStats.Stat.CHARISMA, 5, PrimaryStats.Stat.INTELLIGENCE, 3)), Map.entry(Type.LIBRARIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.JOURNALIST, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.WAR_CORRESPONDENT, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.WILLPOWER, 2)), Map.entry(Type.STUDENT, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.LUCK, 1)), Map.entry(Type.RELIGIOUS_LEADER, modifiers(PrimaryStats.Stat.CHARISMA, 6, PrimaryStats.Stat.WILLPOWER, 2)), Map.entry(Type.SOCIAL_WORKER, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.ACTOR, modifiers(PrimaryStats.Stat.CHARISMA, 5, PrimaryStats.Stat.INTELLIGENCE, 2)), Map.entry(Type.STREET_PERFORMER, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.CHARISMA, 3)), Map.entry(Type.SCULPTOR, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.LUCK, 1)), Map.entry(Type.MUSICIAN, modifiers(PrimaryStats.Stat.CHARISMA, 5, PrimaryStats.Stat.LUCK, 1)), Map.entry(Type.STAGEHAND, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.HOMEMADE_WEAPONS_DESIGNER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.THIEF, modifiers(PrimaryStats.Stat.AGILITY, 5, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.SMALL_TIME_CRIMINAL, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.ESCAPED_CONVICT, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.PICKPOCKET, modifiers(PrimaryStats.Stat.AGILITY, 5, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.SMUGGLER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.CHARISMA, 2)), Map.entry(Type.FIREFIGHTER, modifiers(PrimaryStats.Stat.ENDURANCE, 6, PrimaryStats.Stat.STRENGTH, 3)), Map.entry(Type.GRAVEDIGGER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.GAS_STATION_ATTENDANT, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)), Map.entry(Type.SUPERMARKET_MANAGER, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.INTELLIGENCE, 2)), Map.entry(Type.GARBAGE_COLLECTOR, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)), Map.entry(Type.CLEANER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.AGILITY, 2)), Map.entry(Type.OCCULTIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.LUCK, 2)), Map.entry(Type.MEDIUM, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.LUCK, 1)), Map.entry(Type.EXILE_OR_DESERTER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.WILLPOWER, 3)), Map.entry(Type.CULT_LEADER, modifiers(PrimaryStats.Stat.CHARISMA, 7, PrimaryStats.Stat.WILLPOWER, 3)));

    @SafeVarargs
    private static EnumMap<PrimaryStats.Stat, Integer> modifiers(Object... entries) {
        EnumMap<PrimaryStats.Stat, Integer> map = new EnumMap<>(PrimaryStats.Stat.class);
        for (int i = 0; i < entries.length; i += 2) {
            PrimaryStats.Stat stat = (PrimaryStats.Stat) entries[i];
            Integer bonus = (Integer) entries[i + 1];
            map.put(stat, bonus);
        }
        return map;
    }

    /**
     * Devuelve los bonus de stats para el oficio dado
     */
    public static EnumMap<PrimaryStats.Stat, Integer> getModifiersFor(Type type) {
        EnumMap<PrimaryStats.Stat, Integer> copy = new EnumMap<>(PrimaryStats.Stat.class);
        copy.putAll(MODIFIERS.getOrDefault(type, new EnumMap<>(PrimaryStats.Stat.class)));
        return copy;
    }

    // ───────────── Win95 UI factories ────────────────────────────────

    private void ensureWin95Styles(Skin sk) {
        if (sk.has("font-win95", BitmapFont.class) && sk.has("font-win95-title", BitmapFont.class) && sk.has("win95", TextButton.TextButtonStyle.class) && sk.has("win95-default", TextButton.TextButtonStyle.class)) {
            return;
        }

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/IBMPlexSans-Regular.ttf"));
        FreeTypeFontParameter p14 = new FreeTypeFontParameter();
        p14.size = 14;
        p14.hinting = FreeTypeFontGenerator.Hinting.None;
        p14.minFilter = TextureFilter.Nearest;
        p14.magFilter = TextureFilter.Nearest;
        BitmapFont font14 = gen.generateFont(p14);

        FreeTypeFontParameter p18 = new FreeTypeFontParameter();
        p18.size = 18;
        p18.hinting = FreeTypeFontGenerator.Hinting.None;
        p18.minFilter = TextureFilter.Nearest;
        p18.magFilter = TextureFilter.Nearest;
        BitmapFont font18 = gen.generateFont(p18);

        gen.dispose();

        sk.add("font-win95", font14, BitmapFont.class);
        sk.add("font-win95-title", font18, BitmapFont.class);

        sk.add("win95-label-black", new Label.LabelStyle(font14, WIN95_TEXT));
        sk.add("win95-label-blue", new Label.LabelStyle(font14, WIN95_TITLE));
        sk.add("win95-title-label", new Label.LabelStyle(font18, Color.WHITE));

        NinePatchDrawable up = new NinePatchDrawable(makeBtnBg(false));
        NinePatchDrawable down = new NinePatchDrawable(makeBtnBg(true));
        TextButton.TextButtonStyle normal = new TextButton.TextButtonStyle(up, down, null, null);
        normal.font = font14;
        normal.fontColor = WIN95_TEXT;
        sk.add("win95", normal);

        NinePatchDrawable upDef = new NinePatchDrawable(makeBtnBgDefault(false));
        NinePatchDrawable downDef = new NinePatchDrawable(makeBtnBgDefault(true));
        TextButton.TextButtonStyle def = new TextButton.TextButtonStyle(upDef, downDef, null, null);
        def.font = font14;
        def.fontColor = WIN95_TEXT;
        sk.add("win95-default", def);
    }

    private NinePatch makeWin95Frame() {
        int S = 64, B = 6;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_FACE);
        pm.fill();
        pm.setColor(WIN95_HIGHLIGHT);
        pm.drawLine(0, 0, S - 1, 0);
        pm.drawLine(0, 1, 0, S - 1);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, S - 1, S - 1, S - 1);
        pm.drawLine(S - 1, 0, S - 1, S - 1);
        pm.setColor(WIN95_SHADOW);
        pm.drawRectangle(1, 1, S - 2, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return np;
    }

    private Texture makeTitleBackground() {
        Pixmap pm = new Pixmap(8, 30, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_TITLE);
        pm.fill();
        return new Texture(pm);
    }

    private NinePatch makeBtnBg(boolean pressed) {
        int S = 32, B = 4;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);
        pm.setColor(WIN95_FACE);
        pm.fill();
        pm.setColor(!pressed ? WIN95_HIGHLIGHT : WIN95_SHADOW);
        pm.drawLine(0, 0, S - 2, 0);
        pm.drawLine(0, 1, 0, S - 2);
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(0, S - 1, S - 1, S - 1);
        pm.drawLine(S - 1, 0, S - 1, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return np;
    }

    private NinePatch makeBtnBgDefault(boolean pressed) {
        int S = 34, B = 4;
        Pixmap pm = new Pixmap(S, S, Pixmap.Format.RGBA8888);
        pm.setColor(Color.BLACK);
        pm.fill();
        pm.setColor(WIN95_FACE);
        pm.fillRectangle(1, 1, S - 2, S - 2);
        if (!pressed) {
            pm.setColor(WIN95_HIGHLIGHT);
            pm.drawLine(1, 1, S - 3, 1);
            pm.drawLine(1, 2, 1, S - 3);
        } else {
            pm.setColor(WIN95_SHADOW);
            pm.drawLine(1, 1, S - 3, 1);
            pm.drawLine(1, 2, 1, S - 3);
        }
        pm.setColor(WIN95_DARKSHADOW);
        pm.drawLine(1, S - 2, S - 2, S - 2);
        pm.drawLine(S - 2, 1, S - 2, S - 2);
        NinePatch np = new NinePatch(new Texture(pm), B, B, B, B);
        return np;
    }
}

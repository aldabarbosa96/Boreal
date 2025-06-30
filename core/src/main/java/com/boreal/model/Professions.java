package com.boreal.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Define profesiones y sus modificadores de stats.
 */
public final class Professions implements Serializable {

    public enum Type {
        MECHANIC("Mechanic"),
        ELECTRICIAN("Electrician"),
        PLUMBER("Plumber"),
        CARPENTER("Carpenter"),
        WELDER("Welder"),
        MASON("Mason"),
        BLACKSMITH("Blacksmith"),
        MAINTENANCE_TECHNICIAN("Maintenance Technician"),
        SCRAP_DEALER("Scrap Dealer"),
        LOCKSMITH("Locksmith"),
        BIKER("Biker"),
        MACHINERY_ASSEMBLER("Machinery Assembler"),
        REFRIGERATION_TECHNICIAN("Refrigeration Technician"),
        SOLAR_TECHNICIAN("Solar Technician"),
        RADIO_TECHNICIAN("Radio Technician"),
        SOLDIER("Soldier"),
        POLICE("Police"),
        PRIVATE_SECURITY("Private Security"),
        MILITIAMAN("Militiaman"),
        FILM_SPECIALIST("Film Specialist"),
        SPORT_ARCHER("Sport Archer"),
        BOUNTY_HUNTER("Bounty Hunter"),
        PRIVATE_DETECTIVE("Private Detective"),
        HUNTER("Hunter"),
        FISHERMAN("Fisherman"),
        FARMER("Farmer"),
        AGRICULTURAL_WORKER("Agricultural Worker"),
        PLANT_FORAGER("Plant Forager"),
        FORESTER("Forester"),
        SCOUT("Scout"),
        SURVIVALIST("Survivalist"),
        NOMADIC_TRAVELER("Nomadic Traveler"),
        WANDERER("Wanderer"),
        PROFESSIONAL_CLIMBER("Professional Climber"),
        SURVIVAL_INSTRUCTOR("Survival Instructor"),
        MOUNTAIN_RESCUER("Mountain Rescuer"),
        BOATMAN("Boatman"),
        MEDIC("Medic"),
        SURGEON("Surgeon"),
        NURSE("Nurse"),
        PARAMEDIC("Paramedic"),
        VETERINARIAN("Veterinarian"),
        PHARMACIST("Pharmacist"),
        HERBALIST("Herbalist"),
        PSYCHOLOGIST("Psychologist"),
        THERAPIST("Therapist"),
        COOK("Cook"),
        BUTCHER("Butcher"),
        BAKER("Baker"),
        CANNABIS_GROWER("Cannabis Grower"),
        GARDENER("Gardener"),
        BEEKEEPER("Beekeeper"),
        TRUCK_DRIVER("Truck Driver"),
        BUS_DRIVER("Bus Driver"),
        PILOT("Pilot"),
        SAILOR("Sailor"),
        DELIVERY_DRIVER("Delivery Driver"),
        FAIRGROUND_WORKER("Fairground Worker"),
        SCIENTIST("Scientist"),
        ENGINEER("Engineer"),
        BIOLOGIST("Biologist"),
        CHEMIST("Chemist"),
        PROGRAMMER("Programmer"),
        LAB_TECHNICIAN("Lab Technician"),
        HACKER("Hacker"),
        MINER("Miner"),
        SURVEYOR("Surveyor"),
        RADIO_BROADCASTER("Radio Broadcaster"),
        TEACHER("Teacher"),
        LIBRARIAN("Librarian"),
        JOURNALIST("Journalist"),
        WAR_CORRESPONDENT("War Correspondent"),
        STUDENT("Student"),
        RELIGIOUS_LEADER("Religious Leader"),
        SOCIAL_WORKER("Social Worker"),
        ACTOR("Actor"),
        SCULPTOR("Sculptor"),
        MUSICIAN("Musician"),
        THIEF("Thief"),
        ESCAPED_CONVICT("Escaped Convict"),
        PICKPOCKET("Pickpocket"),
        SMUGGLER("Smuggler"),
        FIREFIGHTER("Firefighter"),
        GRAVEDIGGER("Gravedigger"),
        GAS_STATION_ATTENDANT("Gas Station Attendant"),
        SUPERMARKET_MANAGER("Supermarket Manager"),
        GARBAGE_COLLECTOR("Garbage Collector"),
        CLEANER("Cleaner"),
        OCCULTIST("Occultist"),
        MEDIUM("Medium"),
        EXILE_OR_DESERTER("Exile/Deserter"),
        CULT_LEADER("Cult Leader");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }

        /**
         * Ruta relativa al PNG en assets
         */
        public String iconPath() {
            return "icons/professions/" + name().toLowerCase() + ".png";
        }
    }

    /**
     * Mapa de bonuses por profesión
     */
    private static final Map<Type, EnumMap<PrimaryStats.Stat, Integer>> MODIFIERS = Map.<Type, EnumMap<PrimaryStats.Stat, Integer>>ofEntries(
        Map.entry(Type.MECHANIC, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.AGILITY, 3, PrimaryStats.Stat.ENDURANCE, 2)),
        Map.entry(Type.ELECTRICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.PLUMBER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.CARPENTER, modifiers(PrimaryStats.Stat.STRENGTH, 4, PrimaryStats.Stat.AGILITY, 3)),
        Map.entry(Type.WELDER, modifiers(PrimaryStats.Stat.STRENGTH, 5, PrimaryStats.Stat.ENDURANCE, 3)),
        Map.entry(Type.MASON, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 3)),
        Map.entry(Type.BLACKSMITH, modifiers(PrimaryStats.Stat.STRENGTH, 6, PrimaryStats.Stat.ENDURANCE, 4)),
        Map.entry(Type.MAINTENANCE_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.AGILITY, 2)),
        Map.entry(Type.SCRAP_DEALER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.LOCKSMITH, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.AGILITY, 2)),
        Map.entry(Type.MACHINERY_ASSEMBLER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.REFRIGERATION_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.ENDURANCE, 2)),
        Map.entry(Type.SOLAR_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.RADIO_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.SOLDIER, modifiers(PrimaryStats.Stat.STRENGTH, 6, PrimaryStats.Stat.ENDURANCE, 5)),
        Map.entry(Type.POLICE, modifiers(PrimaryStats.Stat.STRENGTH, 5, PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.CHARISMA, 1)),
        Map.entry(Type.PRIVATE_SECURITY, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.MILITIAMAN, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.STRENGTH, 3)),
        Map.entry(Type.FILM_SPECIALIST, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.INTELLIGENCE, 3)),
        Map.entry(Type.SPORT_ARCHER, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.AGILITY, 2)),
        Map.entry(Type.BOUNTY_HUNTER, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.ENDURANCE, 2)),
        Map.entry(Type.PRIVATE_DETECTIVE, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.INTELLIGENCE, 3)),
        Map.entry(Type.HUNTER, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.FISHERMAN, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.ENDURANCE, 2)),
        Map.entry(Type.FARMER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.AGRICULTURAL_WORKER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.PLANT_FORAGER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.AGILITY, 2)),
        Map.entry(Type.FORESTER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.SCOUT, modifiers(PrimaryStats.Stat.AGILITY, 6, PrimaryStats.Stat.PERCEPTION, 4)),
        Map.entry(Type.SURVIVALIST, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.INTELLIGENCE, 3)),
        Map.entry(Type.NOMADIC_TRAVELER, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.WANDERER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.PROFESSIONAL_CLIMBER, modifiers(PrimaryStats.Stat.AGILITY, 6, PrimaryStats.Stat.ENDURANCE, 3)),
        Map.entry(Type.SURVIVAL_INSTRUCTOR, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.CHARISMA, 2)),
        Map.entry(Type.MOUNTAIN_RESCUER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.BOATMAN, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.MEDIC, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3, PrimaryStats.Stat.CHARISMA, 2)),
        Map.entry(Type.SURGEON, modifiers(PrimaryStats.Stat.INTELLIGENCE, 7, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.NURSE, modifiers(PrimaryStats.Stat.CHARISMA, 3, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.PARAMEDIC, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.VETERINARIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.PHARMACIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.HERBALIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.PSYCHOLOGIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.CHARISMA, 3)),
        Map.entry(Type.THERAPIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.CHARISMA, 3)),
        Map.entry(Type.COOK, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.CHARISMA, 2)),
        Map.entry(Type.BUTCHER, modifiers(PrimaryStats.Stat.STRENGTH, 4, PrimaryStats.Stat.ENDURANCE, 2)),
        Map.entry(Type.BAKER, modifiers(PrimaryStats.Stat.ENDURANCE, 3, PrimaryStats.Stat.INTELLIGENCE, 2)),
        Map.entry(Type.CANNABIS_GROWER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.GARDENER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.ENDURANCE, 2)),
        Map.entry(Type.BEEKEEPER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.TRUCK_DRIVER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.BUS_DRIVER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.PILOT, modifiers(PrimaryStats.Stat.AGILITY, 4, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.SAILOR, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.BIKER, modifiers(PrimaryStats.Stat.AGILITY, 5, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.DELIVERY_DRIVER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.AGILITY, 2)),
        Map.entry(Type.FAIRGROUND_WORKER, modifiers(PrimaryStats.Stat.ENDURANCE, 3, PrimaryStats.Stat.CHARISMA, 2)),
        Map.entry(Type.SCIENTIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 7, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.ENGINEER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.BIOLOGIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.CHEMIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.PROGRAMMER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 7, PrimaryStats.Stat.WILLPOWER, 2)),
        Map.entry(Type.LAB_TECHNICIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 5, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.HACKER, modifiers(PrimaryStats.Stat.INTELLIGENCE, 6, PrimaryStats.Stat.PERCEPTION, 3)),
        Map.entry(Type.MINER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.SURVEYOR, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.INTELLIGENCE, 2)),
        Map.entry(Type.RADIO_BROADCASTER, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.TEACHER, modifiers(PrimaryStats.Stat.CHARISMA, 5, PrimaryStats.Stat.INTELLIGENCE, 3)),
        Map.entry(Type.LIBRARIAN, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.JOURNALIST, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.CHARISMA, 2)),
        Map.entry(Type.WAR_CORRESPONDENT, modifiers(PrimaryStats.Stat.PERCEPTION, 6, PrimaryStats.Stat.WILLPOWER, 2)),
        Map.entry(Type.STUDENT, modifiers(PrimaryStats.Stat.INTELLIGENCE, 3, PrimaryStats.Stat.LUCK, 1)),
        Map.entry(Type.RELIGIOUS_LEADER, modifiers(PrimaryStats.Stat.CHARISMA, 6, PrimaryStats.Stat.WILLPOWER, 2)),
        Map.entry(Type.SOCIAL_WORKER, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.ACTOR, modifiers(PrimaryStats.Stat.CHARISMA, 5, PrimaryStats.Stat.INTELLIGENCE, 2)),
        Map.entry(Type.SCULPTOR, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.LUCK, 1)),
        Map.entry(Type.MUSICIAN, modifiers(PrimaryStats.Stat.CHARISMA, 5, PrimaryStats.Stat.LUCK, 1)),
        Map.entry(Type.THIEF, modifiers(PrimaryStats.Stat.AGILITY, 5, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.ESCAPED_CONVICT, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.PICKPOCKET, modifiers(PrimaryStats.Stat.AGILITY, 5, PrimaryStats.Stat.PERCEPTION, 2, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.SMUGGLER, modifiers(PrimaryStats.Stat.PERCEPTION, 4, PrimaryStats.Stat.CHARISMA, 2)),
        Map.entry(Type.FIREFIGHTER, modifiers(PrimaryStats.Stat.ENDURANCE, 6, PrimaryStats.Stat.STRENGTH, 3)),
        Map.entry(Type.GRAVEDIGGER, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.GAS_STATION_ATTENDANT, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.PERCEPTION, 2)),
        Map.entry(Type.SUPERMARKET_MANAGER, modifiers(PrimaryStats.Stat.CHARISMA, 4, PrimaryStats.Stat.INTELLIGENCE, 2)),
        Map.entry(Type.GARBAGE_COLLECTOR, modifiers(PrimaryStats.Stat.ENDURANCE, 5, PrimaryStats.Stat.STRENGTH, 2)),
        Map.entry(Type.CLEANER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.AGILITY, 2)),
        Map.entry(Type.OCCULTIST, modifiers(PrimaryStats.Stat.INTELLIGENCE, 4, PrimaryStats.Stat.LUCK, 2)),
        Map.entry(Type.MEDIUM, modifiers(PrimaryStats.Stat.PERCEPTION, 5, PrimaryStats.Stat.LUCK, 1)),
        Map.entry(Type.EXILE_OR_DESERTER, modifiers(PrimaryStats.Stat.ENDURANCE, 4, PrimaryStats.Stat.WILLPOWER, 3)),
        Map.entry(Type.CULT_LEADER, modifiers(PrimaryStats.Stat.CHARISMA, 7, PrimaryStats.Stat.WILLPOWER, 3))
    );

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
     * Devuelve copia de los modifiers para esa profesión
     */
    public static EnumMap<PrimaryStats.Stat, Integer> getModifiersFor(Type t) {
        EnumMap<PrimaryStats.Stat, Integer> copy = new EnumMap<>(PrimaryStats.Stat.class);
        copy.putAll(MODIFIERS.getOrDefault(t, new EnumMap<>(PrimaryStats.Stat.class)));
        return copy;
    }

    private Professions() { /* no instancias */ }
}

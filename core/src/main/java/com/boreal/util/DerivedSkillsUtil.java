package com.boreal.util;

import com.boreal.model.Professions;

import java.util.EnumMap;
import java.util.List;

/**
 * Mapa de habilidades derivadas/secundarias por profesión.
 */
public final class DerivedSkillsUtil {
    private static final EnumMap<Professions.Type, List<String>> SKILLS_MAP = new EnumMap<>(Professions.Type.class);

    static {
        SKILLS_MAP.put(Professions.Type.MECHANIC, List.of("Soldadura de estructuras", "Fabricación de herramientas", "Reparación de maquinaria"));
        SKILLS_MAP.put(Professions.Type.ELECTRICIAN, List.of("Instalación de energía solar", "Manipulación de sistemas hidráulicos", "Soldadura de estructuras", "Desmontaje de chatarra"));
        SKILLS_MAP.put(Professions.Type.PLUMBER, List.of("Reprogramación de sistemas", "Ajuste fino de maquinaria", "Instalación de energía solar", "Desmontaje de chatarra", "Configuración de radios", "Reparación de maquinaria"));
        SKILLS_MAP.put(Professions.Type.CARPENTER, List.of("Ajuste fino de maquinaria", "Instalación eléctrica", "Manipulación de sistemas hidráulicos", "Reforzado de puertas y ventanas"));
        SKILLS_MAP.put(Professions.Type.WELDER, List.of("Instalación eléctrica", "Instalación de energía solar", "Desmontaje de chatarra", "Ensamblaje de componentes", "Reparación de vehículos"));
        SKILLS_MAP.put(Professions.Type.MASON, List.of("Instalación eléctrica", "Desmontaje de chatarra", "Diagnóstico técnico", "Albañilería", "Fortificación defensiva"));
        SKILLS_MAP.put(Professions.Type.BLACKSMITH, List.of("Reparación de maquinaria", "Desmontaje de chatarra", "Reparación de vehículos", "Ensamblaje de componentes", "Instalación de trampas", "Instalación de energía solar"));
        SKILLS_MAP.put(Professions.Type.MAINTENANCE_TECHNICIAN, List.of("Reparación de maquinaria", "Ajuste fino de maquinaria", "Manipulación de sistemas hidráulicos", "Desmontaje de chatarra", "Instalación eléctrica", "Instalación de energía solar"));
        SKILLS_MAP.put(Professions.Type.SCRAP_DEALER, List.of("Gestión de inventario", "Organización de almacenes", "Optimización de recursos", "Control de acceso", "Distribución de suministros", "Logística de transporte"));
        SKILLS_MAP.put(Professions.Type.LOCKSMITH, List.of("Asignación de tareas", "Supervisión de turnos", "Optimización de recursos"));
        SKILLS_MAP.put(Professions.Type.BIKER, List.of("Pilotaje básico (avión, dron, helicóptero)", "Navegación marítima", "Conducción en condiciones adversas", "Reparación de motores", "Conducción de coches", "Detección de trampas en ruta"));
        SKILLS_MAP.put(Professions.Type.MACHINERY_ASSEMBLER, List.of("Instalación eléctrica", "Configuración de radios", "Soldadura de estructuras", "Reparación de maquinaria"));
        SKILLS_MAP.put(Professions.Type.REFRIGERATION_TECHNICIAN, List.of("Fabricación de herramientas", "Configuración de radios", "Diagnóstico técnico", "Mantenimiento general", "Reparación de vehículos", "Reparación de maquinaria", "Desmontaje de chatarra"));
        SKILLS_MAP.put(Professions.Type.SOLAR_TECHNICIAN, List.of("Instalación de energía solar", "Diagnóstico técnico", "Instalación eléctrica", "Mantenimiento general", "Soldadura de estructuras", "Ajuste fino de maquinaria"));
        SKILLS_MAP.put(Professions.Type.RADIO_TECHNICIAN, List.of("Soldadura de estructuras", "Ensamblaje de componentes", "Diagnóstico técnico", "Configuración de radios", "Ajuste fino de maquinaria", "Mantenimiento general", "Reparación de vehículos"));
        SKILLS_MAP.put(Professions.Type.SOLDIER, List.of("Lucha en espacios cerrados", "Precisión con armas a distancia", "Manejo de explosivos", "Resistencia física", "Defensa personal", "Recarga y mantenimiento de armas"));
        SKILLS_MAP.put(Professions.Type.POLICE, List.of("Uso de armas improvisadas", "Reflejos de combate", "Manejo de explosivos"));
        SKILLS_MAP.put(Professions.Type.PRIVATE_SECURITY, List.of("Caza humana (tracking hostil)", "Lucha en espacios cerrados", "Uso de armas improvisadas", "Control del pánico", "Defensa personal", "Precisión con armas a distancia", "Combate cuerpo a cuerpo"));
        SKILLS_MAP.put(Professions.Type.MILITIAMAN, List.of("Combate cuerpo a cuerpo", "Control del pánico", "Combate con cuchillos"));
        SKILLS_MAP.put(Professions.Type.FILM_SPECIALIST, List.of("Uso de radios de corto alcance", "Transmisión de emergencia", "Manipulación de drones", "Encriptación básica", "Uso de ordenadores antiguos"));
        SKILLS_MAP.put(Professions.Type.SPORT_ARCHER, List.of("Manejo de explosivos", "Resistencia física", "Combate cuerpo a cuerpo"));
        SKILLS_MAP.put(Professions.Type.BOUNTY_HUNTER, List.of("Manejo de armas de fuego", "Lucha en espacios cerrados", "Control del pánico", "Cobertura y sigilo táctico", "Recarga y mantenimiento de armas", "Uso de armas improvisadas"));
        SKILLS_MAP.put(Professions.Type.PRIVATE_DETECTIVE, List.of("Organización de almacenes", "Gestión de inventario", "Asignación de tareas"));
        SKILLS_MAP.put(Professions.Type.HUNTER, List.of("Combate cuerpo a cuerpo", "Combate con cuchillos", "Manejo de armas de fuego", "Recarga y mantenimiento de armas", "Reflejos de combate", "Uso de armas improvisadas"));
        SKILLS_MAP.put(Professions.Type.FISHERMAN, List.of("Preparación de alimentos al aire libre", "Escalada", "Rastreo de presas", "Cultivo y agricultura", "Filtrado y purificación de agua", "Reconocimiento de flora tóxica", "Encendido de fuego"));
        SKILLS_MAP.put(Professions.Type.FARMER, List.of("Caza de animales", "Reconocimiento de flora tóxica", "Cultivo y agricultura"));
        SKILLS_MAP.put(Professions.Type.AGRICULTURAL_WORKER, List.of("Disciplina mental", "Conocimiento zoológico", "Negociación bajo presión", "Codificación de mensajes", "Escritura de informes", "Formación de novatos"));
        SKILLS_MAP.put(Professions.Type.PLANT_FORAGER, List.of("Filtrado y purificación de agua", "Construcción de refugio natural", "Reconocimiento de flora tóxica", "Encendido de fuego", "Cultivo y agricultura", "Preparación de alimentos al aire libre"));
        SKILLS_MAP.put(Professions.Type.FORESTER, List.of("Optimización de recursos", "Control de acceso", "Logística de transporte", "Asignación de tareas", "Distribución de suministros"));
        SKILLS_MAP.put(Professions.Type.SCOUT, List.of("Preparación de alimentos al aire libre", "Pesca", "Caza de animales"));
        SKILLS_MAP.put(Professions.Type.SURVIVALIST, List.of("Encendido de fuego", "Escalada", "Conservación de alimentos", "Pesca"));
        SKILLS_MAP.put(Professions.Type.NOMADIC_TRAVELER, List.of("Conservación de alimentos", "Encendido de fuego", "Reconocimiento de flora tóxica", "Construcción de refugio natural"));
        SKILLS_MAP.put(Professions.Type.WANDERER, List.of("Encendido de fuego", "Creación de trampas primitivas", "Escalada", "Preparación de alimentos al aire libre", "Reconocimiento de flora tóxica", "Domesticación de animales"));
        SKILLS_MAP.put(Professions.Type.PROFESSIONAL_CLIMBER, List.of("Preparación de alimentos al aire libre", "Rastreo de presas", "Filtrado y purificación de agua", "Construcción de refugio natural", "Caza de animales", "Reconocimiento de flora tóxica"));
        SKILLS_MAP.put(Professions.Type.SURVIVAL_INSTRUCTOR, List.of("Evaluación de necesidades básicas", "Asignación de tareas", "Gestión de inventario", "Clasificación de materiales"));
        SKILLS_MAP.put(Professions.Type.MOUNTAIN_RESCUER, List.of("Construcción de refugio natural", "Escalada", "Caza de animales", "Preparación de alimentos al aire libre", "Creación de trampas primitivas"));
        SKILLS_MAP.put(Professions.Type.BOATMAN, List.of("Pesca", "Cultivo y agricultura", "Preparación de alimentos al aire libre"));
        SKILLS_MAP.put(Professions.Type.MEDIC, List.of("Recolección de muestras", "Cirugía de emergencia", "Tratamiento de heridas", "Investigación médica", "Biología básica"));
        SKILLS_MAP.put(Professions.Type.SURGEON, List.of("Identificación de infecciones", "Preparación de medicamentos", "Uso de equipo médico", "Biología básica", "Administración de fármacos", "Recolección de muestras", "Tratamiento psicológico"));
        SKILLS_MAP.put(Professions.Type.NURSE, List.of("Investigación médica", "Tratamiento de heridas", "Aplicación de inyecciones", "Identificación de infecciones"));
        SKILLS_MAP.put(Professions.Type.PARAMEDIC, List.of("Preparación de medicamentos", "Uso de equipo médico", "Primeros auxilios"));
        SKILLS_MAP.put(Professions.Type.VETERINARIAN, List.of("Tratamiento de heridas", "Uso de equipo médico", "Investigación médica", "Aplicación de inyecciones", "Primeros auxilios", "Reanimación"));
        SKILLS_MAP.put(Professions.Type.PHARMACIST, List.of("Tratamiento de heridas", "Aplicación de inyecciones", "Extracción de proyectiles"));
        SKILLS_MAP.put(Professions.Type.HERBALIST, List.of("Recolección de muestras", "Cirugía de emergencia", "Investigación médica", "Biología básica", "Administración de fármacos", "Uso de equipo médico", "Tratamiento de heridas"));
        SKILLS_MAP.put(Professions.Type.PSYCHOLOGIST, List.of("Manejo de la moral del grupo", "Gestión de recursos humanos", "Cartografía manual", "Disciplina mental", "Liderazgo en crise, gestión de equipo"));
        SKILLS_MAP.put(Professions.Type.THERAPIST, List.of("Conocimiento religioso", "Motivación grupal", "Gestión de equipo", "Escritura de informes", "Evaluación ética de decisiones", "Manipulación de la verdad"));
        SKILLS_MAP.put(Professions.Type.COOK, List.of("Cultivo y agricultura", "Evaluación de necesidades básicas", "Domesticación de animales"));
        SKILLS_MAP.put(Professions.Type.BUTCHER, List.of("Logística de transporte", "Orientación sin GPS", "Encendido de fuego"));
        SKILLS_MAP.put(Professions.Type.BAKER, List.of("Construcción de refugio natural", "Gestión de inventario", "Control de acceso", "Escalada", "Conservación de alimentos", "Caza de animales", "Asignación de tareas"));
        SKILLS_MAP.put(Professions.Type.CANNABIS_GROWER, List.of("Recolección de plantas comestibles", "Logística de transporte", "Reconocimiento de flora tóxica", "Rastreo de presas", "Pesca"));
        SKILLS_MAP.put(Professions.Type.GARDENER, List.of("Encendido de fuego", "Preparación de alimentos al aire libre", "Pesca", "Conservación de alimentos", "Caza de animales"));
        SKILLS_MAP.put(Professions.Type.BEEKEEPER, List.of("Recolección de plantas comestibles", "Gestión de inventario", "Escalada", "Domesticación de animales", "Preparación de alimentos al aire libre", "Asignación de tareas"));
        SKILLS_MAP.put(Professions.Type.TRUCK_DRIVER, List.of("Navegación marítima", "Maniobra de camiones y furgones", "Repostaje manual"));
        SKILLS_MAP.put(Professions.Type.BUS_DRIVER, List.of("Repostaje manual", "Reparación de motores", "Detección de trampas en ruta"));
        SKILLS_MAP.put(Professions.Type.PILOT, List.of("Repostaje manual", "Maniobra de camiones y furgones", "Transporte de mercancías seguras", "Pilotaje básico (avión, dron, helicóptero)"));
        SKILLS_MAP.put(Professions.Type.SAILOR, List.of("Repostaje manual", "Maniobra de camiones y furgones", "Conducción de coches", "Conducción evasiva", "Transporte de mercancías seguras", "Pilotaje básico (avión, dron, helicóptero)", "Detección de trampas en ruta"));
        SKILLS_MAP.put(Professions.Type.DELIVERY_DRIVER, List.of("Maniobra de camiones y furgones", "Conducción evasiva", "Navegación marítima", "Repostabilidad manual", "Conducción de coches", "Pilotaje básico (avión, dron, helicóptero)", "Conducción en condiciones adversas"));
        SKILLS_MAP.put(Professions.Type.FAIRGROUND_WORKER, List.of("Distribución de suministros", "Evaluación de necesidades básicas", "Supervisión de turnos"));
        SKILLS_MAP.put(Professions.Type.SCIENTIST, List.of("Liderazgo en crisis", "Gestión de equipo", "Formación de novatos", "Gestión de recursos humanos", "Codificación de mensajes"));
        SKILLS_MAP.put(Professions.Type.ENGINEER, List.of("Reprogramación de sistemas", "Configuración de radios", "Mantenimiento general", "Instalación eléctrica"));
        SKILLS_MAP.put(Professions.Type.BIOLOGIST, List.of("Conocimiento zoológico", "Evaluación de amenazas sociales", "Persuasión"));
        SKILLS_MAP.put(Professions.Type.CHEMIST, List.of("Codificación de mensajes", "Liderazgo en crisis", "Manipulación de la verdad", "Evaluación de amenazas sociales"));
        SKILLS_MAP.put(Professions.Type.PROGRAMMER, List.of("Gestión de equipo", "Motivación grupal", "Negociación bajo presión"));
        SKILLS_MAP.put(Professions.Type.LAB_TECHNICIAN, List.of("Manipulación de la verdad", "Reparación de vehículos", "Reparación de maquinaria", "Disciplina mental", "Fabricación de herramientas", "Escritura de informes"));
        SKILLS_MAP.put(Professions.Type.HACKER, List.of("Gestión de equipo", "Traducción de lenguas", "Codificación de mensajes", "Análisis de documentos", "Motivación grupal", "Conocimiento zoológico"));
        SKILLS_MAP.put(Professions.Type.MINER, List.of("Logística de transporte", "Asignación de tareas", "Evaluación de necesidades básicas"));
        SKILLS_MAP.put(Professions.Type.SURVEYOR, List.of("Asignación de tareas", "Logística de transporte", "Evaluación de necesidades básicas", "Control de acceso", "Supervisión de turnos", "Distribución de suministros", "Optimización de recursos"));
        SKILLS_MAP.put(Professions.Type.RADIO_BROADCASTER, List.of("Reparación de maquinaria", "Persuasión", "Configuración de radios"));
        SKILLS_MAP.put(Professions.Type.TEACHER, List.of("Disciplina mental", "Manipulación de la verdad", "Manejo de la moral del grupo", "Análisis de documentos", "Conocimiento botánico", "Gestión de equipo"));
        SKILLS_MAP.put(Professions.Type.LIBRARIAN, List.of("Conocimiento religioso", "Empatía", "Evaluación ética de decisiones"));
        SKILLS_MAP.put(Professions.Type.JOURNALIST, List.of("Evaluación ética de decisiones", "Conocimiento zoológico", "Traducción de lenguas"));
        SKILLS_MAP.put(Professions.Type.WAR_CORRESPONDENT, List.of("Manipulación de drones", "Uso de radios de corto alcance", "Encriptación básica"));
        SKILLS_MAP.put(Professions.Type.STUDENT, List.of("Gestión de recursos humanos", "Traducción de lenguas", "Cartografía manual"));
        SKILLS_MAP.put(Professions.Type.RELIGIOUS_LEADER, List.of("Codificación de mensajes", "Psicología de trauma", "Análisis de documentos", "Traducción de lenguas"));
        SKILLS_MAP.put(Professions.Type.SOCIAL_WORKER, List.of("Conocimiento botánico", "Traducción de lenguas", "Negociación bajo presión", "Análisis de documentos", "Liderazgo en crisis", "Conocimientos históricos"));
        SKILLS_MAP.put(Professions.Type.ACTOR, List.of("Control de acceso", "Logística de transporte", "Optimización de recursos", "Gestión de inventario", "Organización de almacenes", "Evaluación de necesidades básicas"));
        SKILLS_MAP.put(Professions.Type.SCULPTOR, List.of("Logística de transporte", "Control de acceso", "Gestión de inventario", "Organización de almacenes"));
        SKILLS_MAP.put(Professions.Type.MUSICIAN, List.of("Evaluación de necesidades básicas", "Organización de almacenes", "Gestión de inventario", "Logística de transporte", "Clasificación de materiales"));
        SKILLS_MAP.put(Professions.Type.THIEF, List.of("Ocultación de rastro", "Fabricación de ganzúas", "Forzar cerraduras", "Robar sin ser visto"));
        SKILLS_MAP.put(Professions.Type.ESCAPED_CONVICT, List.of("Sabotaje de equipos", "Engaño y distracción", "Desactivación de alarmas", "Ocultación de rastro", "Infiltración silenciosa", "Desactivación de trampas"));
        SKILLS_MAP.put(Professions.Type.PICKPOCKET, List.of("Contrabando de objetos", "Evasión en zonas hostiles", "Sabotaje de equipos", "Desactivación de trampas", "Ocultación de rastro", "Escapismo"));
        SKILLS_MAP.put(Professions.Type.SMUGGLER, List.of("Sabotaje de equipos", "Engaño y distracción", "Ocultación de rastro", "Infiltración silenciosa", "Fabricación de ganzúas", "Escapismo"));
        SKILLS_MAP.put(Professions.Type.FIREFIGHTER, List.of("Gestión de inventario", "Distribución de suministros", "Asignación de tareas", "Supervisión de turnos", "Evaluación de necesidades básicas"));
        SKILLS_MAP.put(Professions.Type.GRAVEDIGGER, List.of("Control de acceso", "Evaluación de necesidades básicas", "Clasificación de materiales"));
        SKILLS_MAP.put(Professions.Type.GAS_STATION_ATTENDANT, List.of("Gestión de inventario", "Distribución de suministros", "Logística de transporte", "Control de acceso"));
        SKILLS_MAP.put(Professions.Type.SUPERMARKET_MANAGER, List.of("Gestión de inventario", "Distribución de suministros", "Evaluación de necesidades básicas", "Logística de transporte", "Control de acceso"));
        SKILLS_MAP.put(Professions.Type.GARBAGE_COLLECTOR, List.of("Supervisión de turnos", "Optimización de recursos", "Control de acceso"));
        SKILLS_MAP.put(Professions.Type.CLEANER, List.of("Gestión de inventario", "Organización de almacenes", "Clasificación de materiales"));
        SKILLS_MAP.put(Professions.Type.OCCULTIST, List.of("Cartografía manual", "Motivación grupal", "Disciplina mental"));
        SKILLS_MAP.put(Professions.Type.MEDIUM, List.of("Enseñanza y transmisión de saber", "Traducción de lenguas", "Persuasión", "Negociación bajo presión"));
        SKILLS_MAP.put(Professions.Type.EXILE_OR_DESERTER, List.of("Gestión de inventario", "Distribución de suministros", "Control de acceso"));
        SKILLS_MAP.put(Professions.Type.CULT_LEADER, List.of("Liderazgo en crisis", "Evaluación de amenazas sociales", "Coordinación por radio", "Cartografía manual", "Conocimiento botánico", "Enseñanza y transmisión de saber", "Disciplina mental"));
    }

    private DerivedSkillsUtil() { /* no instancias */ }

    /**
     * Devuelve la lista de habilidades derivadas para una profesión dada.
     */
    public static List<String> getDerivedSkills(Professions.Type profession) {
        return SKILLS_MAP.getOrDefault(profession, List.of());
    }
}

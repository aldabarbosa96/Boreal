package com.boreal.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Representa todas las habilidades derivadas definidas en DerivedSkillsUtil.
 * Cada habilidad tendrá un nivel numérico entre MIN y MAX.
 * Se puede aplicar un nivel base (por profesiones) y luego distribuir puntos extra.
 */
public final class Skills implements Serializable {

    public enum Skill {
        ADMINISTRACION_DE_FARMACOS("Administración de fármacos"),
        AJUSTE_FINO_DE_MAQUINARIA("Ajuste fino de maquinaria"),
        ALBANILERIA("Albañilería"),
        ANALISIS_DE_DOCUMENTOS("Análisis de documentos"),
        APLICACION_DE_INYECCIONES("Aplicación de inyecciones"),
        ASIGNACION_DE_TAREAS("Asignación de tareas"),
        BIOLOGIA_BASICA("Biología básica"),
        CARTOGRAFIA_MANUAL("Cartografía manual"),
        CAZA_DE_ANIMALES("Caza de animales"),
        CAZA_HUMANA_TRACKING_HOSTIL("Caza humana (tracking hostil)"),
        CIRUGIA_DE_EMERGENCIA("Cirugía de emergencia"),
        CLASIFICACION_DE_MATERIALES("Clasificación de materiales"),
        COBERTURA_Y_SIGILO_TACTICO("Cobertura y sigilo táctico"),
        CODIFICACION_DE_MENSAJES("Codificación de mensajes"),
        COMBATE_CON_CUCHILLOS("Combate con cuchillos"),
        COMBATE_CUERPO_A_CUERPO("Combate cuerpo a cuerpo"),
        CONDUCCION_DE_COCHES("Conducción de coches"),
        CONDUCCION_EN_CONDICIONES_ADVERSAS("Conducción en condiciones adversas"),
        CONDUCCION_EVASIVA("Conducción evasiva"),
        CONFIGURACION_DE_RADIOS("Configuración de radios"),
        CONOCIMIENTO_BOTANICO("Conocimiento botánico"),
        CONOCIMIENTO_RELIGIOSO("Conocimiento religioso"),
        CONOCIMIENTO_ZOOLOGICO("Conocimiento zoológico"),
        CONOCIMIENTOS_HISTORICOS("Conocimientos históricos"),
        CONSERVACION_DE_ALIMENTOS("Conservación de alimentos"),
        CONSTRUCCION_DE_REFUGIO_NATURAL("Construcción de refugio natural"),
        CONTRABANDO_DE_OBJETOS("Contrabando de objetos"),
        CONTROL_DE_ACCESO("Control de acceso"),
        CONTROL_DEL_PANICO("Control del pánico"),
        COORDINACION_POR_RADIO("Coordinación por radio"),
        CREACION_DE_TRAMPAS_PRIMITIVAS("Creación de trampas primitivas"),
        CULTIVO_Y_AGRICULTURA("Cultivo y agricultura"),
        DEFENSA_PERSONAL("Defensa personal"),
        DESACTIVACION_DE_ALARMAS("Desactivación de alarmas"),
        DESACTIVACION_DE_TRAMPAS("Desactivación de trampas"),
        DESMONTAJE_DE_CHATARRA("Desmontaje de chatarra"),
        DETECCION_DE_TRAMPAS_EN_RUTA("Detección de trampas en ruta"),
        DIAGNOSTICO_TECNICO("Diagnóstico técnico"),
        DISCIPLINA_MENTAL("Disciplina mental"),
        DISTRIBUCION_DE_SUMINISTROS("Distribución de suministros"),
        DOMESTICACION_DE_ANIMALES("Domesticación de animales"),
        EMPATIA("Empatía"),
        ENCENDIDO_DE_FUEGO("Encendido de fuego"),
        ENCRIPTACION_BASICA("Encriptación básica"),
        ENGANO_Y_DISTRACCION("Engaño y distracción"),
        ENSAMBLAJE_DE_COMPONENTES("Ensamblaje de componentes"),
        ENSENANZA_Y_TRANSMISION_DE_SABER("Enseñanza y transmisión de saber"),
        ESCALADA("Escalada"),
        ESCAPISMO("Escapismo"),
        ESCRITURA_DE_INFORMES("Escritura de informes"),
        EVALUACION_DE_AMENAZAS_SOCIALES("Evaluación de amenazas sociales"),
        EVALUACION_DE_NECESIDADES_BASICAS("Evaluación de necesidades básicas"),
        EVALUACION_ETICA_DE_DECISIONES("Evaluación ética de decisiones"),
        EVASION_EN_ZONAS_HOSTILES("Evasión en zonas hostiles"),
        EXTRACCION_DE_PROYECTILES("Extracción de proyectiles"),
        FABRICACION_DE_GANZUAS("Fabricación de ganzúas"),
        FABRICACION_DE_HERRAMIENTAS("Fabricación de herramientas"),
        FILTRADO_Y_PURIFICACION_DE_AGUA("Filtrado y purificación de agua"),
        FORMACION_DE_NOVATOS("Formación de novatos"),
        FORTIFICACION_DEFENSIVA("Fortificación defensiva"),
        FORZAR_CERRADURAS("Forzar cerraduras"),
        GESTION_DE_EQUIPO("Gestión de equipo"),
        GESTION_DE_INVENTARIO("Gestión de inventario"),
        GESTION_DE_RECURSOS_HUMANOS("Gestión de recursos humanos"),
        IDENTIFICACION_DE_INFECCIONES("Identificación de infecciones"),
        INFILTRACION_SILENCIOSA("Infiltración silenciosa"),
        INSTALACION_DE_ENERGIA_SOLAR("Instalación de energía solar"),
        INSTALACION_DE_TRAMPAS("Instalación de trampas"),
        INSTALACION_ELECTRICA("Instalación eléctrica"),
        INVESTIGACION_MEDICA("Investigación médica"),
        LIDERAZGO_EN_CRISIS("Liderazgo en crisis"),
        LIDERAZGO_EN_CRISIS_GESTION_DE_EQUIPO("Liderazgo en crisis, gestión de equipo"),
        LOGISTICA_DE_TRANSPORTE("Logística de transporte"),
        LUCHA_EN_ESPACIOS_CERRADOS("Lucha en espacios cerrados"),
        MANEJO_DE_ARMAS_DE_FUEGO("Manejo de armas de fuego"),
        MANEJO_DE_EXPLOSIVOS("Manejo de explosivos"),
        MANEJO_DE_LA_MORAL_DEL_GRUPO("Manejo de la moral del grupo"),
        MANIOBRA_DE_CAMIONES_Y_FURGONES("Maniobra de camiones y furgones"),
        MANIPULACION_DE_DRONES("Manipulación de drones"),
        MANIPULACION_DE_LA_VERDAD("Manipulación de la verdad"),
        MANIPULACION_DE_SISTEMAS_HIDRAULICOS("Manipulación de sistemas hidráulicos"),
        MANTENIMIENTO_GENERAL("Mantenimiento general"),
        MOTIVACION_GRUPAL("Motivación grupal"),
        NAVEGACION_MARITIMA("Navegación marítima"),
        NEGOCIACION_BAJO_PRESION("Negociación bajo presión"),
        OCULTACION_DE_RASTRO("Ocultación de rastro"),
        OPTIMIZACION_DE_RECURSOS("Optimización de recursos"),
        ORGANIZACION_DE_ALMACENES("Organización de almacenes"),
        ORIENTACION_SIN_GPS("Orientación sin GPS"),
        PERSUASION("Persuasión"),
        PESCA("Pesca"),
        PILOTAJE_BASICO_AVION_DRON_HELICOPTERO("Pilotaje básico (avión, dron, helicóptero)"),
        PRECISION_CON_ARMAS_A_DISTANCIA("Precisión con armas a distancia"),
        PREPARACION_DE_ALIMENTOS_AL_AIRE_LIBRE("Preparación de alimentos al aire libre"),
        PREPARACION_DE_MEDICAMENTOS("Preparación de medicamentos"),
        PRIMEROS_AUXILIOS("Primeros auxilios"),
        PSICOLOGIA_DE_TRAUMA("Psicología de trauma"),
        RASTREO_DE_PRESAS("Rastreo de presas"),
        REANIMACION("Reanimación"),
        RECARGA_Y_MANTENIMIENTO_DE_ARMAS("Recarga y mantenimiento de armas"),
        RECOLECCION_DE_MUESTRAS("Recolección de muestras"),
        RECOLECCION_DE_PLANTAS_COMESTIBLES("Recolección de plantas comestibles"),
        RECONOCIMIENTO_DE_FLORA_TOXICA("Reconocimiento de flora tóxica"),
        REFLEJOS_DE_COMBATE("Reflejos de combate"),
        REFORZADO_DE_PUERTAS_Y_VENTANAS("Reforzado de puertas y ventanas"),
        REPARACION_DE_MAQUINARIA("Reparación de maquinaria"),
        REPARACION_DE_MOTORES("Reparación de motores"),
        REPARACION_DE_VEHICULOS("Reparación de vehículos"),
        REPOSTAJE_MANUAL("Repostaje manual"),
        REPROGRAMACION_DE_SISTEMAS("Reprogramación de sistemas"),
        RESISTENCIA_FISICA("Resistencia física"),
        ROBAR_SIN_SER_VISTO("Robar sin ser visto"),
        SABOTAJE_DE_EQUIPOS("Sabotaje de equipos"),
        SOLDADURA_DE_ESTRUCTURAS("Soldadura de estructuras"),
        SUPERVISION_DE_TURNOS("Supervisión de turnos"),
        TRADUCCION_DE_LENGUAS("Traducción de lenguas"),
        TRANSMISION_DE_EMERGENCIA("Transmisión de emergencia"),
        TRANSPORTE_DE_MERCANCIAS_SEGURAS("Transporte de mercancías seguras"),
        TRATAMIENTO_DE_HERIDAS("Tratamiento de heridas"),
        TRATAMIENTO_PSICOLOGICO("Tratamiento psicológico"),
        USO_DE_ARMAS_IMPROVISADAS("Uso de armas improvisadas"),
        USO_DE_EQUIPO_MEDICO("Uso de equipo médico"),
        USO_DE_ORDENADORES_ANTIGUOS("Uso de ordenadores antiguos"),
        USO_DE_RADIOS_DE_CORTO_ALCANCE("Uso de radios de corto alcance");

        private final String label;
        Skill(String label) { this.label = label; }
        public String label() { return label; }
        @Override public String toString() { return label; }
    }

    // ===== CONFIGURACIÓN =====
    public static final int MIN = 0;
    public static final int MAX = 10;

    /** Nivel base asignado (por profesión) */
    private final EnumMap<Skill, Integer> baseLevels = new EnumMap<>(Skill.class);
    /** Nivel actual (baseLevels + puntos repartidos por el jugador) */
    private final EnumMap<Skill, Integer> values = new EnumMap<>(Skill.class);
    /** Puntos restantes para repartir tras aplicar el base */
    private int remainingPoints = 0;

    public Skills() {
        reset();
    }

    /** Vuelve todo a 0 y sin puntos para repartir */
    public void reset() {
        for (Skill s : Skill.values()) {
            baseLevels.put(s, MIN);
            values.put(s, MIN);
        }
        remainingPoints = 0;
    }

    public void applyBase(Map<Skill,Integer> basePoints) {
        for (var entry : basePoints.entrySet()) {
            Skill s = entry.getKey();
            int points = entry.getValue();
            int b = Math.min(MAX, baseLevels.get(s) + points);
            baseLevels.put(s, b);
            values.put(s, b);
        }
    }

    /**
     * Inicializa los puntos que el jugador puede repartir libremente.
     * Debe invocarse *después* de todas las llamadas a applyBase().
     *
     * @param totalPoints puntos libres que el jugador podrá repartir
     */
    public void setDistributablePoints(int totalPoints) {
        this.remainingPoints = totalPoints;
    }

    /** Devuelve cuántos puntos le quedan al jugador para repartir */
    public int getRemainingPoints() {
        return remainingPoints;
    }

    /**
     * Intenta subir +1 nivel de la habilidad si hay puntos restantes
     * y no se supera MAX.
     */
    public boolean raise(Skill s) {
        int cur = values.get(s);
        if (remainingPoints > 0 && cur < MAX) {
            values.put(s, cur + 1);
            remainingPoints--;
            return true;
        }
        return false;
    }

    /**
     * Intenta bajar –1 nivel de la habilidad si no baja del nivel base
     * y reembolsa un punto repartible.
     */
    public boolean lower(Skill s) {
        int cur = values.get(s);
        int base = baseLevels.get(s);
        if (cur > base) {
            values.put(s, cur - 1);
            remainingPoints++;
            return true;
        }
        return false;
    }

    /** Obtiene el nivel actual de la habilidad */
    public int get(Skill s) {
        return values.get(s);
    }

    /** Copia inmutable de todos los niveles actuales */
    public Map<Skill, Integer> asMap() {
        return Map.copyOf(values);
    }

    @Override
    public String toString() {
        return "Skills{" +
            "values=" + values +
            ", base=" + baseLevels +
            ", remaining=" + remainingPoints +
            '}';
    }
}

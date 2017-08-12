package io.lyra.oghma.common.io;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Directory constants.
 *
 * @author Cian.
 */
public class DirectoryConst {


    static final Path ROOT_DIR = Paths.get(".oghma");

    public static final Path CONTENT_DIR        = Paths.get("content");
    public static final Path PACK_DIR           = Paths.get("pack");
    static final        Path INSTALLED_PACK_DIR = PACK_DIR.resolve("installed");
    public static final Path RELEASE_DIR        = Paths.get("release");

    public static final Path DATA_DIR     = Paths.get("data");
    public static final Path UNIVERSE_DIR = DATA_DIR.resolve("universe");
    public static final Path LIFEFORM_DIR = DATA_DIR.resolve("lifeform");
    public static final Path SERVER_DIR   = DATA_DIR.resolve("server");

    public static final Path LANGUAGE_DIR = CONTENT_DIR.resolve("language");

    public static final Path SCHEMA_DIR          = CONTENT_DIR.resolve("schema");
    public static final Path FORM_SCHEMA_DIR     = SCHEMA_DIR.resolve("form");
    public static final Path MATERIAL_SCHEMA_DIR = SCHEMA_DIR.resolve("material");
    public static final Path ITEM_SCHEMA_DIR     = SCHEMA_DIR.resolve("item");
    public static final Path FLORA_SCHEMA_DIR    = SCHEMA_DIR.resolve("flora");
    public static final Path CLIMATE_SCHEMA_DIR  = SCHEMA_DIR.resolve("climate");
    public static final Path GRASS_SCHEMA_DIR    = SCHEMA_DIR.resolve("grass");

    private static final Path AUDIO_DIR             = CONTENT_DIR.resolve("audio");
    public static final  Path MUSIC_AUDIO_DIR       = AUDIO_DIR.resolve("music");
    public static final  Path ENVIRONMENT_AUDIO_DIR = AUDIO_DIR.resolve("environment");
    public static final  Path EFFECT_AUDIO_DIR      = AUDIO_DIR.resolve("effect");

    private static final Path GRAPHIC_DIR = CONTENT_DIR.resolve("graphic");
    public static final  Path UI_DIR      = GRAPHIC_DIR.resolve("ui");
    public static final  Path SHADER_DIR  = GRAPHIC_DIR.resolve("shader");
    public static final  Path TEXTURE_DIR = GRAPHIC_DIR.resolve("texture");


}

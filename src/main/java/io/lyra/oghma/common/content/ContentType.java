package io.lyra.oghma.common.content;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Optional;

import static io.lyra.oghma.common.io.DirectoryConst.*;

/**
 * Specifies the different pack file types, the file extensions
 * they're identified by and the paths they should be extracted to.
 * <p>
 * Copyright © 2016 Cian O'Mahony. All rights reserved.
 *
 * @author Cian O'Mahony
 */
public enum ContentType {

    FORM("osf", FORM_SCHEMA_DIR, true),
    MATERIAL("osm", MATERIAL_SCHEMA_DIR, true),
    CLIMATE("osc", CLIMATE_SCHEMA_DIR, true),
    ITEM("osi", ITEM_SCHEMA_DIR, true),
    FLORA("osr", FLORA_SCHEMA_DIR, true),
    GRASS("osg", GRASS_SCHEMA_DIR, true),
    MUSIC("oam", MUSIC_AUDIO_DIR, true),
    ENVIRONMENT("oan", ENVIRONMENT_AUDIO_DIR, true),
    EFFECT("oae", EFFECT_AUDIO_DIR, true),
    UI("ogu", UI_DIR, true),
    TEXTURE("png", TEXTURE_DIR, false),
    SHADER("ogs", SHADER_DIR, true),
    PACK("op", PACK_DIR, true);


    private final String  extension;
    private final Path    contentPath;
    private final boolean versioned;

    ContentType(final String extension, final Path contentPath, final boolean versioned) {
        this.extension = extension;
        this.contentPath = contentPath;
        this.versioned = versioned;
    }

    public static Optional<ContentType> fromFilePath(final String fileName) {
        final int i = fileName.lastIndexOf('.');
        if (i > 0 && fileName.length() - i >= 3) {
            String extChars = fileName.substring(i + 1, i + 3);
            return EnumSet.allOf(ContentType.class).stream()
                    .filter(type -> type.getExtension().equalsIgnoreCase(extChars))
                    .findFirst();
        }
        return Optional.empty();
    }

    public boolean isVersioned() {
        return versioned;
    }

    public boolean accept(final int allowedVersion, final String fileName) {
        final String expectedSuffix = "." + extension + (isVersioned() ? allowedVersion : "");
        return fileName.endsWith(expectedSuffix);
    }

    public Path toFilePath(final int version, final String fileName) {
        final String fullFileName = fileName + "." + extension + (isVersioned() ? version : "");
        return contentPath.resolve(fullFileName);
    }

    public static boolean isPack(final String fileName) {
        return fromFilePath(fileName).orElse(null) == ContentType.PACK;
    }

    public static EnumSet<ContentType> packTypes() {
        return EnumSet.of(FORM, MATERIAL, CLIMATE, ITEM, FLORA, GRASS, MUSIC, ENVIRONMENT, EFFECT, UI, TEXTURE, SHADER);
    }

    public String getExtension() {
        return extension;
    }

    public Path getContentPath() {
        return contentPath;
    }

}

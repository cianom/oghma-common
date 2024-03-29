package io.lyra.oghma.common.content;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Optional;

import static io.lyra.oghma.common.io.DirectoryConst.*;

/**
 * Specifies the different pack file types, the file extensions
 * they're identified by and the paths they should be extracted to.
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


    private final String extension;
    private final Path contentPath;
    private final boolean versioned;

    ContentType(final String extension, final Path contentPath, final boolean versioned) {
        this.extension = extension;
        this.contentPath = contentPath;
        this.versioned = versioned;
    }

    public static ExtensionAndVersion getExtensionAndVersion(final String fileName) {
        final int i = fileName.lastIndexOf('.');
        if (i > 0 && fileName.length() - i >= 2) {
            final String fullExtension = fileName.substring(i + 1, fileName.length());

            int v = fullExtension.length();
            boolean digits = true;
            int version = 0;
            String extChars = "";
            while (--v >= 0) {
                final char c = fullExtension.charAt(v);
                if (digits && Character.isDigit(c)) {
                    version += Integer.parseInt(Character.toString(c)) * (Math.pow(10, fullExtension.length() - 1 - v));
                } else {
                    extChars = c + extChars;
                    digits = false;
                }
            }
            return new ExtensionAndVersion(extChars, version);
        }
        return null;
    }

    public static Optional<ContentType> fromFilePath(final String fileName) {
        final ExtensionAndVersion extensionAndVersion = getExtensionAndVersion(fileName);
        if (extensionAndVersion != null) {
            return EnumSet.allOf(ContentType.class).stream()
                    .filter(type -> type.getExtension().equalsIgnoreCase(extensionAndVersion.extChars))
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

    static class ExtensionAndVersion {

        final String extChars;
        final int version;


        public ExtensionAndVersion(final String extChars, final int version) {
            this.extChars = extChars;
            this.version = version;
        }
    }
}

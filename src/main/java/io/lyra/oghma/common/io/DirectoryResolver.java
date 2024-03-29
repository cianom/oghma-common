package io.lyra.oghma.common.io;

import io.lyra.oghma.common.content.ContentType;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import static io.lyra.oghma.common.io.DirectoryConst.*;

/**
 * Resolves directories and path for different content operations.
 */
public class DirectoryResolver {

    private static final String META_FILENAME    = "oghma-{0}.json";
    private static final String RELEASE_FILENAME = "oghma-{0}.jar";

    private final Path oghmaRoot;

    private DirectoryResolver(final Path oghmaRoot) {
        this.oghmaRoot = oghmaRoot;
    }

    public static DirectoryResolver ofRoot(final Path root) {
        return new DirectoryResolver(root);
    }

    public static DirectoryResolver ofDefaultRoot() {
        Path oghmaRoot = computeOghmaRoot();

        return new DirectoryResolver(oghmaRoot);
    }

    private static Path computeOghmaRoot() {
        final Path userHome = Paths.get(System.getProperty("user.home"));
        final String os = System.getProperty("os.name");

        Path oghmaRoot;
        if (os.startsWith("Windows")) {
            oghmaRoot = userHome.resolve("AppData/Roaming/").resolve(ROOT_DIR);
            if (!oghmaRoot.toFile().exists()) {
                oghmaRoot = userHome.resolve(ROOT_DIR);
            }
        }
        else {
            oghmaRoot = userHome.resolve(ROOT_DIR);
        }
        return oghmaRoot;
    }

    public Path getRoot() {
        return oghmaRoot;
    }

    public List<Path> getAllRequiredDirectories() {
        final List<Path> allRequired = new ArrayList<>(Collections.singletonList(getRoot()));

        EnumSet.allOf(ContentType.class).stream()
                .map(ContentType::getContentPath)
                .map(getRoot()::resolve)
                .forEach(allRequired::add);

        allRequired.add(getInstalledDir());
        allRequired.add(getReleasesRoot());

        return allRequired;
    }

    public boolean isInstalled(final String filePath, final String sha256Hash) {
        return getInstalledPath(filePath, sha256Hash).toFile().exists();
    }

    public Path resolveRelativeRoot(final Path file) {
        return getRoot().resolve(file);
    }

    public Path resolveRelativeRoot(final String file) {
        return getRoot().resolve(file);
    }

    public Path getDirectory(final ContentType type) {
        return resolve(type.getContentPath(), false);
    }

    public Path resolve(final Path file, final boolean relative) {
        return (relative) ? file : resolveRelativeRoot(file);
    }

    public Path getInstalledDir() {
        return getRoot().resolve(INSTALLED_PACK_DIR);
    }

    public Path getInstalledPath(final String filePath, final String sha256Hash) {
        final Path contentPath = Paths.get(filePath);
        final String shortHash = sha256Hash.substring(0, Math.min(10, sha256Hash.length()));
        final String installedFileName = shortHash + "_" + contentPath.getFileName();
        return getInstalledDir().resolve(installedFileName);
    }

    public Path computeExtractPath(final String fileName) {
        return ContentType.fromFilePath(fileName)
                .map(type -> getRoot().resolve(type.getContentPath()))
                .orElse(null);
    }

    public Path getReleaseDir(final String version) {
        return getReleasesRoot().resolve(version);
    }

    public File getReleaseMeta(final String version) {
        final String metaName = MessageFormat.format(META_FILENAME, version);
        return getReleaseDir(version).resolve(metaName).toFile();
    }

    public File getReleaseBinary(final String version) {
        final String metaName = MessageFormat.format(RELEASE_FILENAME, version);
        return getReleaseDir(version).resolve(metaName).toFile();
    }

    public Path getReleasesRoot() {
        return getRoot().resolve(RELEASE_DIR);
    }


}

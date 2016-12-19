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

/**
 * Resolves directories and path for different content operations.
 * <p>
 * Copyright © 2016 Cian O'Mahony. All rights reserved.
 *
 * @author Cian O'Mahony
 */
public class DirectoryResolver {

    private static final String META_FILENAME      = "oghma-{0}.json";
    private static final String RELEASE_FILENAME   = "oghma-{0}.jar";
    private static final String INSTALLED_PACK_DIR = "pack/installed/";

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

    public static Path computeOghmaRoot() {
        final String oghmaDirName = ".oghma";
        final Path userHome = Paths.get(System.getProperty("user.home"));
        final String os = System.getProperty("os.name");

        Path oghmaRoot;
        if (os.startsWith("Windows")) {
            oghmaRoot = userHome.resolve("AppData/Roaming/").resolve(oghmaDirName);
            if (!oghmaRoot.toFile().exists()) {
                oghmaRoot = userHome.resolve(oghmaDirName);
            }
        }
        else {
            oghmaRoot = userHome.resolve(oghmaDirName);
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

    public Path resolveRelativeRoot(final String file) {
        return getRoot().resolve(file);
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
        return getRoot().resolve("release/");
    }


}

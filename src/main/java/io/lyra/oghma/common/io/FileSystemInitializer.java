package io.lyra.oghma.common.io;

import io.lyra.oghma.common.InitializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

/**
 * Ensures existence of directory structure required to download and install content.
 */
public class FileSystemInitializer {

    private DirectoryResolver dirResolver;

    public FileSystemInitializer(final DirectoryResolver dirResolver) {
        this.dirResolver = dirResolver;
    }

    public void setUpFileSystemStructure() throws InitializationException {
        dirResolver.getAllRequiredDirectories().forEach(this::safeCreateDir);
    }

    private void safeCreateDir(final Path directory) throws InitializationException {
        try {
            if (!Files.exists(directory)) Files.createDirectories(directory);
        }
        catch (IOException e) {
            throw new InitializationException(MessageFormat.format("Could not create directory [{0}]", directory), e);
        }
    }

}

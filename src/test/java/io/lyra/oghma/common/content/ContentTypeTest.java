package io.lyra.oghma.common.content;

import java.nio.file.Path;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link ContentType}.
 *
 * @author Cian.
 */
public class ContentTypeTest {

    @org.junit.Test
    public void fromFilePath() throws Exception {
        for (final ContentType type : ContentType.values()) {
            final int version = new Random().nextInt(50);
            final Path filePath = type.toFilePath(version, "asndl");
            final ContentType returned = ContentType.fromFilePath(filePath.getFileName().toString()).orElse(null);
            assertEquals(type, returned);
        }
    }

}
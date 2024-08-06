package comp3350.intellicards.tests.utils;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Configuration;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/Intellicards.script");

    public static File copyTestDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");

        copy(DB_SRC.toPath(), target.toPath(), REPLACE_EXISTING);

        Configuration.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}

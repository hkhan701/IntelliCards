package comp3350.intellicards.tests.utils;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Main;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/Intellicards.script");
    private static final File EMPTY_DB_SRC = new File("src/main/assets/db/IntellicardsTest.script");;

    public static File copyTestDB(boolean useNullDB) throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        if (useNullDB) {
            copy(EMPTY_DB_SRC.toPath(), target.toPath(), REPLACE_EXISTING);
        } else {
            copy(DB_SRC.toPath(), target.toPath(), REPLACE_EXISTING);
        }
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}

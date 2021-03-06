package covid.businesslogiclayer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseConfigurationTest {
    DatabaseConfiguration db = new DatabaseConfiguration();

    @TempDir
    public File folder;

    @Test
    void getUrlClassPass() {
        assertEquals("jdbc:mariadb://localhost:3306/ClosingProject?useUnicode=true", db.getUrl());
    }

    @Test
    void getUserClassPass() {
        assertEquals("alma", db.getUser());
    }

    @Test
    void getPasswordClassPass() {
        assertEquals("alma", db.getPassword());

    }

    @Test
    public void testExternalFile() throws IOException {
        File file = new File(folder, "test.txt");
        try (
                PrintWriter printWriter = new PrintWriter(file, "UTF-8");
        ) {
            printWriter.println("db.url = 192.168.10.20");
            printWriter.println("db.password = 1111");
            printWriter.println("db.user = ütvefúrógép");
        }

        DatabaseConfiguration db = new DatabaseConfiguration(file);
        assertEquals("192.168.10.20", db.getUrl());
        assertEquals("1111", db.getPassword());
        assertEquals("ütvefúrógép", db.getUser());
    }
}


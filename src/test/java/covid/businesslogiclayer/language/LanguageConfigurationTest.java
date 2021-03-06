package covid.businesslogiclayer.language;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageConfigurationTest {

    @Test
    void get1HUN() {
        LanguageConfiguration l = new LanguageConfiguration("/HUN.txt");

    assertEquals("alma", l.getMessage("test"));

    }


    @Test
    void get1ENG() {
        LanguageConfiguration l = new LanguageConfiguration("/eng.txt");

        assertEquals("apple", l.getMessage("test"));

    }

@Test
    void getStart(){
    LanguageConfiguration l = new LanguageConfiguration();

    assertEquals("Adja meg a nyelvet/Select a language:", l.getMessage("select"));

    }


}
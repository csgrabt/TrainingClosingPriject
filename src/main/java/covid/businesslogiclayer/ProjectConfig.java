package covid.businesslogiclayer;

import covid.businesslogiclayer.language.LanguageConfiguration;
import covid.dataacceslayer.CitizenDao;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectConfig {
    public  final String MENU_CONTROL = "1";
    private final List<String> menuPoints = List.of("1", "2", "3", "4", "5", "6", "7");
    private final Citizen cz = new Citizen();
    private final Scanner scanner = new Scanner(System.in);
    private final CitizenDao cd = new CitizenDao();
    private final List<String> vaccinaMenu = List.of("A", "B", "C", "D", "E");
    private final List<String> languageMenu = List.of("A", "B");
    private LanguageConfiguration language = new LanguageConfiguration();

    public String getMENU_CONTROL() {
        return MENU_CONTROL;
    }

    public void setLanguage(LanguageConfiguration lg) {
        this.language = lg;
    }

    public LanguageConfiguration getLanguage() {
        return language;
    }

    public List<String> getLanguageMenu() {
        return new ArrayList<>(languageMenu);
    }

    public CitizenDao getCd() {
        return cd;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public List<String> getMenuPoints() {
        return new ArrayList<>(menuPoints);
    }

    public Citizen getCz() {
        return cz;
    }

    public List<String> getVaccinaMenu() {
        return new ArrayList<>(vaccinaMenu);
    }

    public static MariaDbDataSource createDbConnection(MariaDbDataSource dataSource) {
        DatabaseConfiguration dc = new DatabaseConfiguration();
        try {
            dataSource.setUrl(dc.getUrl());
            dataSource.setUser(dc.getUser());
            dataSource.setPassword(dc.getPassword());

        } catch (SQLException se) {
            throw new IllegalArgumentException("Some problem with dataSource", se);
        }
        return dataSource;
    }

}






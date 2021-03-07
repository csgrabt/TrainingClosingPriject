package covid;

import covid.businesslogiclayer.Citizen;
import covid.businesslogiclayer.ProjectConfig;
import covid.businesslogiclayer.VaccinesType;
import covid.dataacceslayer.CitizenDao;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CitizenDaoTest {
    ProjectConfig pc = new ProjectConfig();
    MariaDbDataSource dataSource = new MariaDbDataSource();
    CitizenDao cd = new CitizenDao();
    Citizen citizen;
    Flyway flyway;
    MariaDbDataSource dataSource1 = new MariaDbDataSource();
    File file1;


    @TempDir
    public File folder;

    @BeforeEach
    void setTest() throws FileNotFoundException, UnsupportedEncodingException {
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/ClosingProject?useUnicode=true");
            dataSource.setUser("alma");
            dataSource.setPassword("alma");
        } catch (SQLException se) {
            throw new IllegalArgumentException("Some problem with dataSource", se);
        }
        try {
            dataSource1.setUrl("jdbc:mariadb://localhost:3306/ClosingProject?useUnicode=true");
            dataSource1.setUser("alma");
            dataSource1.setPassword("alma1");
        } catch (SQLException se) {
            throw new IllegalArgumentException("Some problem with dataSource", se);
        }

        flyway = Flyway.configure().dataSource(pc.getCd().getDataSource()).load();
        flyway.clean();
        flyway.migrate();
        citizen = new Citizen("Kiss Géza", "1007", 35, "m@m", "000000000");

        file1 = new File(folder, "test.txt");
        try (
                PrintWriter printWriter = new PrintWriter(file1, "UTF-8");
        ) {
            printWriter.println("Név;Irányítószám;Életkor;E-mail cím;TAJ-szám");
            printWriter.println("Daryl Chasier;5400;86;dchasier0@comcast.net;123456788");
            printWriter.println("Sunny Stanlake;5400;86;sstanlake1@etsy.com;123456795");
            printWriter.println("Kristofor Brigginshaw;5400;75;kbrigginshaw2@patch.com;123456805");
            printWriter.println("Trever Spennock;5400;76;tspennock3@yellowbook.com;123456812");
            printWriter.println("Beckie Middleweek;5400;32;bmiddleweek4@opensource.org;123456829");
            printWriter.println("Orly McKeran;6224;54;omckeran5@fotki.com;123456836");

        }

    }


    @Test
    void zipCodeTestOK() {
        assertEquals("Budapest", pc.getCd().findCityByZipCode("1007"));
        assertEquals("Mezőtúr", pc.getCd().findCityByZipCode("5400"));
    }

    @Test
    void zipCodeTestZipIsNotExits() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cd.findCityByZipCode("1000");
        });
        assertEquals("Db does not contain the ZipCode!", ex.getMessage());
    }

    @Test
    void writeRegistrationToDb() {
        cd.writeRegistrationToDB(citizen);

        assertEquals(1, cd.searchCitizenIdBasedOnTaj("000000000"));
    }


    @Test
    void writeRegisterFromFileToDbTest() {
        cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), ";");
        assertEquals(1, cd.searchCitizenIdBasedOnTaj("123456788"));
        assertEquals(2, cd.searchCitizenIdBasedOnTaj("123456795"));
        assertEquals(3, cd.searchCitizenIdBasedOnTaj("123456805"));
        assertEquals(4, cd.searchCitizenIdBasedOnTaj("123456812"));
        assertEquals(5, cd.searchCitizenIdBasedOnTaj("123456829"));
        assertEquals(6, cd.searchCitizenIdBasedOnTaj("123456836"));
    }

    @Test
    void writeRegisterFromFileToDbTestBadRegex() {
        Exception ex1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), "\n");
        });
        assertEquals("Rollback, there is an error in the 2th line!", ex1.getMessage());
    }


    @Test
    void numberOfVaccination() {
        cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), ";");
        assertEquals(0, cd.numberOfVaccination("123456812"));

    }


    @Test
    void numberOfVaccinationTableNotContainedTajLengthIsWrong() {
        Exception ex1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), ";");
            cd.numberOfVaccination("1234568120");
        });
        assertEquals("The length of the insurance number is wrong!", ex1.getMessage());

    }

    @Test
    void numberOfVaccinationTableNotContainedTaj() {
        Exception ex1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), ";");
            cd.numberOfVaccination("000000000");
        });
        assertEquals("The Database does not contained this TAJ number!", ex1.getMessage());

    }


    @Test
    void vaccinationSetTimeAndTypeAllIsOK() {
        cd.writeRegistrationToDB(citizen);
        Citizen cz = new Citizen(
                1,
                "finom",
                1, "ok",
                LocalDate.now()
        );

        cd.vaccinationSetTimeAndType(cz);
        assertEquals("finom", cd.typeOfVaccination("000000000"));
    }


    @Test
    void vaccinationSetTimeAndTypeRollBackTest() {
        Exception ex1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cd.writeRegistrationToDB(citizen);
            Citizen cz = new Citizen(
                    1,
                    "finom",
                    3,
                    "ok",
                    LocalDate.now()
            );


            cd.vaccinationSetTimeAndType(cz);

        });
        assertEquals("Túl sok oltás: 3!", ex1.getMessage());

    }

    @Test
    void dateOfVaccinationTest() {
        cd.writeRegistrationToDB(citizen);
        Citizen cz = new Citizen(
                1,
                "finom",
                1,
                "OK",
                LocalDate.of(2020, 01, 30)
        );


        cd.vaccinationSetTimeAndType(cz);
        assertEquals("2020-01-30 00:00:00.0", cd.dateOfVaccination("000000000"));
    }

    @Test
    void dateOfVaccinationTestByEnum() {
        cd.writeRegistrationToDB(citizen);
        Citizen cz = new Citizen(
                1,
                VaccinesType.D.getName(),
                1,
                "OK",
                LocalDate.of(2020, 01, 30)
        );


        cd.vaccinationSetTimeAndType(cz);
        assertEquals("2020-01-30 00:00:00.0", cd.dateOfVaccination("000000000"));
    }


    @Test
    void dateOfVaccinationTajIsNotExitsInTheTable() {
        cd.writeRegistrationToDB(citizen);

        Citizen cz = new Citizen(
                1,
                "finom",
                1,
                "OK",
                LocalDate.of(2020, 01, 30)
        );

        cd.vaccinationSetTimeAndType(cz);
        assertEquals(null, cd.dateOfVaccination("001100000"));
    }

    @Test
    void failedVaccinationTest() {
        cd.writeRegistrationToDB(citizen);
        cd.failedVaccination(LocalDate.now(), "Várandós", 1, "Not Ok");

        assertEquals("Várandós", cd.noteOfVaccinationFailed("000000000"));

    }


    @Test
    void statisticBasedOnZipTest() {


        cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), ";");
        cd.writeRegistrationToDB(citizen);
        Citizen cz1 = new Citizen(1, "finom", 0, "OK", LocalDate.now());
        Citizen cz2 = new Citizen(1, "finom", 1, "OK", LocalDate.now());
        Citizen cz3 = new Citizen(2, "finom", 0, "OK", LocalDate.now());
        Citizen cz4 = new Citizen(3, "finom", 0, "OK", LocalDate.now());


        cd.vaccinationSetTimeAndType(cz1);
        cd.vaccinationSetTimeAndType(cz2);
        cd.vaccinationSetTimeAndType(cz3);
        cd.vaccinationSetTimeAndType(cz4);

        assertEquals(2, cd.statisticBasedOnZip("5400").get(0));
        assertEquals(2, cd.statisticBasedOnZip("5400").get(1));
        assertEquals(1, cd.statisticBasedOnZip("5400").get(2));

    }


    @Test
    void dailyVaccinationBasedOnZipTest() {


        cd.writeRegisterFromFileToDb(file1.getAbsolutePath(), ";");
        cd.writeRegistrationToDB(citizen);
        Citizen cz1 = new Citizen(1, "finom", 1, "OK", LocalDate.now());
        Citizen cz2 = new Citizen(2, "finom", 0, "OK", LocalDate.of(2000, 10, 10));
        Citizen cz3 = new Citizen(5, "finom", 1, "OK", LocalDate.of(2000, 10, 10));


        cd.vaccinationSetTimeAndType(cz1);
        cd.vaccinationSetTimeAndType(cz2);
        cd.vaccinationSetTimeAndType(cz3);
        assertEquals(3, cd.dailyVaccinationBasedOnZip("5400").size());
    }


}
package covid;

import covid.businesslogiclayer.Citizen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CitizenTest {
    MariaDbDataSource dataSource;

    @BeforeEach
    public void setDataSource() {
        dataSource = new MariaDbDataSource();
        try {

            dataSource.setUrl("jdbc:mariadb://localhost:3306/ClosingProject?useUnicode=true");
            dataSource.setUser("alma");
            dataSource.setPassword("alma");

        } catch (SQLException se) {
            throw new IllegalArgumentException("Some problem with dataSource", se);
        }

    }

    @Test
    void constructorTestWithVaccinationTypeAndNumber() {
        Citizen citizen = new Citizen(

                "Árvíztűrő Tükörfúrógép",
                "5400",
                53,
                "m@m",
                "123456788",
                "Kínai szar",
                5);
        assertEquals("Árvíztűrő Tükörfúrógép", citizen.getFullName());
        assertEquals("5400", citizen.getZipCode());
        assertEquals(53, citizen.getAge());
        assertEquals("m@m", citizen.getEmail());
        assertEquals("123456788", citizen.getHealthInsuranceNumber());
        assertEquals("Kínai szar", citizen.getVaccinationType());
        assertEquals(5, citizen.getNumberOfVaccination());
    }


    @Test
    void constructorTestWithID() {
        Citizen citizen = new Citizen(
                1,
                "Árvíztűrő Tükörfúrógép",
                "5400",
                53,
                "m@m",
                "123456788");
        assertEquals("Árvíztűrő Tükörfúrógép", citizen.getFullName());
        assertEquals("5400", citizen.getZipCode());
        assertEquals(53, citizen.getAge());
        assertEquals("m@m", citizen.getEmail());
        assertEquals("123456788", citizen.getHealthInsuranceNumber());
        assertEquals(1, citizen.getId());
    }


    @Test
    void constructorTest() {
        Citizen citizen = new Citizen(
                "Árvíztűrő Tükörfúrógép",
                "5400",
                53,
                "m@m",
                "123456788");
        assertEquals("Árvíztűrő Tükörfúrógép", citizen.getFullName());
        assertEquals("5400", citizen.getZipCode());
        assertEquals(53, citizen.getAge());
        assertEquals("m@m", citizen.getEmail());
        assertEquals("123456788", citizen.getHealthInsuranceNumber());
    }

    @Test
    void nameTestIsNull() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    null,
                    "5400",
                    53,
                    "m@m",
                    "123456788");
        });
        assertEquals("Name can not be null or empty!", ex.getMessage());

    }

    @Test
    void nameTestIsEmpty() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "",
                    "5400",
                    53,
                    "m@m",
                    "123456788"
            );
        });
        assertEquals("Name can not be null or empty!", ex.getMessage());

    }


    @Test
    void ageTest() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5400",
                    3,
                    "m@m",
                    "123456788"
            );
        });
        assertEquals("Age is not correct!", ex.getMessage());

    }

    @Test
    void emailTestNoAt() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5400",
                    30,
                    "mmm",
                    "123456788"
            );
        });
        assertEquals("Email address is not valid!", ex.getMessage());

    }

    @Test
    void eamilTestShort() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5400",
                    30,
                    "m@",
                    "123456788"
            );
        });
        assertEquals("Email address is not valid!", ex.getMessage());

    }


    @Test
    void healthInsuranceTestNotNumber() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5400",
                    30,
                    "m@",
                    "a12345678"
            );
        });
        assertEquals("One of the Character is not number!", ex.getMessage());

    }

    @Test
    void healthInsuranceTestLength() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5400",
                    30,
                    "m@",
                    "2345678"
            );
        });
        assertEquals("The length of the insurance number is wrong!", ex.getMessage());

    }

    @Test
    void healthInsuranceTestNotCorrect() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5400",
                    30,
                    "m@",
                    "023456708"
            );
        });
        assertEquals("Health insurance number is wrong!", ex.getMessage());

    }


    @Test
    void zipCodeTest() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Citizen(
                    "Kiss Béla",
                    "5401",
                    30,
                    "m@m",
                    "123456788"
            );
        });
        assertEquals("Db does not contain the ZipCode, or digit is not 4!", ex.getMessage());
    }

}
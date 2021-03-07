package covid.dataacceslayer;

import covid.businesslogiclayer.Citizen;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static covid.businesslogiclayer.ProjectConfig.createDbConnection;


public class CitizenDao {
    private final MariaDbDataSource dataSource = new MariaDbDataSource();

    public CitizenDao() {
        createDbConnection(dataSource);
    }

    public MariaDbDataSource getDataSource() {
        return dataSource;
    }

    public String findCityByZipCode(String zipCode) {
        String city;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("select city from zipcodes where zip = ?")
        ) {
            ps.setString(1, zipCode);
            city = getStringFromResultSet(ps);
        } catch (SQLException sql) {
            throw new IllegalArgumentException(sql.getMessage(), sql);
        }
        checkThatDBContainsZipCode(city);
        return city;
    }


    private void checkThatDBContainsZipCode(String city) {
        if (city == null) {
            throw new IllegalArgumentException("Db does not contain the ZipCode!");
        }
    }


    public void writeRegistrationToDB(Citizen citizen) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("insert into citizens(citizen_name, zip, age, email, taj) values (?, ?, ?, ?, ?)")) {
            stmt.setString(1, citizen.getFullName());
            stmt.setString(2, citizen.getZipCode());
            stmt.setInt(3, citizen.getAge());
            stmt.setString(4, citizen.getEmail());
            stmt.setString(5, citizen.getHealthInsuranceNumber());
            stmt.executeUpdate();
        } catch (SQLException se) {
            throw new IllegalArgumentException("Something went wrong during writing database", se);
        }
    }

    public void writeRegisterFromFileToDb(String str, String regex) {
        Path path1 = Path.of(str);
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            int counter = 1;
            try (PreparedStatement stmt = conn.prepareStatement("insert into citizens(citizen_name, zip, age, email, taj) values (?, ?, ?, ?, ?)")) {
                try (BufferedReader bf = Files.newBufferedReader(path1)) {
                    String line;
                    bf.readLine();
                    while ((line = bf.readLine()) != null) {
                        counter++;
                        String[] citizenArray = line.split(regex);
                        Citizen cz = new Citizen(
                                citizenArray[0],
                                citizenArray[1],
                                Integer.parseInt(citizenArray[2]),
                                citizenArray[3],
                                citizenArray[4]
                        );
                        stmt.setString(1, cz.getFullName());
                        stmt.setString(2, cz.getZipCode());
                        stmt.setInt(3, cz.getAge());
                        stmt.setString(4, cz.getEmail());
                        stmt.setString(5, cz.getHealthInsuranceNumber());
                        stmt.executeUpdate();
                    }
                    conn.commit();
                } catch (IOException | ArrayIndexOutOfBoundsException ioe) {
                    throw new IllegalArgumentException("Cannot read file", ioe);
                }
            } catch (IllegalArgumentException ioe) {
                conn.rollback();
                throw new IllegalArgumentException("Rollback, there is an error in the " + counter + "th line!", ioe);
            }
        } catch (SQLException sql) {
            throw new IllegalArgumentException(sql.getMessage(), sql);
        }
    }


    public Integer searchCitizenIdBasedOnTaj(String taj) {
        new Citizen().validatorHealthInsuranceNumber(taj);
        int id;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("select citizen_id from citizens where taj = ?")
        ) {
            ps.setString(1, taj);
            id = getCitizenIdFromDB(ps);
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot select Citizen", sql);
        }
        return id;
    }

    private int getCitizenIdFromDB(PreparedStatement ps) {
        int id;
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt("citizen_id");
            } else {
                throw new IllegalArgumentException("The Database does not contained this TAJ number!");
            }
        } catch (SQLException sql) {
            throw new IllegalArgumentException("No data", sql);
        }
        return id;
    }


    public Integer numberOfVaccination(String taj) {
        int counter = 0;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("select number_of_vaccination from citizens where citizen_id = ?");
        ) {
            ps.setInt(1, searchCitizenIdBasedOnTaj(taj));
            counter = getNumberOfVaccinationDataProcess(counter, ps);
        } catch (SQLException sql) {
            throw new IllegalArgumentException(sql.getMessage(), sql);
        }
        return counter;
    }

    private int getNumberOfVaccinationDataProcess(int counter, PreparedStatement ps) {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String numberOfVaccination = rs.getString(1);
                counter = getTheNumber(numberOfVaccination);
            }
        } catch (SQLException sql) {
            throw new IllegalArgumentException("No data", sql);
        }
        return counter;
    }

    private int getTheNumber(String numberOfVaccination) {
        int counter;
        if (numberOfVaccination == null) {
            counter = 0;
        } else {
            counter = Integer.parseInt(numberOfVaccination);
        }
        return counter;
    }


    public void vaccinationSetTimeAndType(Citizen cz) {
        try (Connection conn = dataSource.getConnection()) {
            setTimeAndTypePreparedStatement(conn, cz);
        } catch (SQLException sql) {
            throw new IllegalArgumentException(sql.getMessage());
        }
    }


    private void setTimeAndTypePreparedStatement(Connection conn, Citizen cz) throws SQLException {
        conn.setAutoCommit(false);
        try (
                PreparedStatement ps =
                        conn.prepareStatement("insert into Vaccinations(citizen_id, vaccination_date, status, vaccination_type) values (?, ?, ?, ?)");
                PreparedStatement ps1 =
                        conn.prepareStatement("Update citizens set  number_of_vaccination = ?, last_vaccination = ? where citizen_id = ? ")) {

            ps.setInt(1, cz.getId());
            ps.setDate(2, Date.valueOf(cz.getLastVaccination()));
            ps.setString(3, cz.getStatus());
            ps.setString(4, cz.getVaccinationType());

            ps.executeUpdate();

            errorHandling(cz);
            ps1.setInt(1, cz.getNumberOfVaccination() + 1);
            ps1.setDate(2, Date.valueOf(cz.getLastVaccination()));
            ps1.setInt(3, cz.getId());
            ps1.executeUpdate();

            conn.commit();
        } catch (SQLException | IllegalStateException se) {
            conn.rollback();
            throw new IllegalArgumentException(se.getMessage(), se);
        }

    }

    private void errorHandling(Citizen cz) {
        if (cz.getNumberOfVaccination() >= 2) {
            throw new IllegalStateException("Túl sok oltás: " + cz.getNumberOfVaccination() + "!");
        }
    }


    public String dateOfVaccination(String taj) {
        String timeOfVaccination;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("select last_vaccination from citizens where taj = ?")) {
            ps.setString(1, taj);

            timeOfVaccination = getStringFromResultSet(ps);

        } catch (SQLException sql) {

            throw new IllegalStateException("SQL Error", sql);
        }


        return timeOfVaccination;
    }

    private String getStringFromResultSet(PreparedStatement ps) {
        String result = null;
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException sql) {
            throw new IllegalArgumentException("No data", sql);
        }
        return result;
    }

    public String typeOfVaccination(String taj) {
        String typeOfVaccina;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("select vaccination_type from vaccinations where citizen_id = ?")
        ) {
            ps.setInt(1, searchCitizenIdBasedOnTaj(taj));

            typeOfVaccina = getStringFromResultSet(ps);

        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot select Citizen based on ID", sql);
        }
        return typeOfVaccina;
    }


    public void failedVaccination(LocalDate date, String note, int id, String status) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("insert into Vaccinations(citizen_id, vaccination_date, status, note) values (?, ?, ?, ?)")
        ) {
            ps.setInt(1, id);
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, status);
            ps.setString(4, note);
            ps.executeUpdate();
        } catch (SQLException se) {
            throw new IllegalArgumentException("Cannot registration the first vaccina", se);
        }
    }

    public List<Integer> statisticBasedOnZip(String zip) {
        List<Integer> result;
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("select number_of_vaccination from citizens where zip = ?")
        ) {
            ps.setString(1, zip);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String numberOfV = rs.getString("number_of_vaccination");

                    if (numberOfV.equals("0")) {
                        counter1++;
                    }
                    if (numberOfV.equals("1")) {
                        counter2++;
                    }
                    if (numberOfV.equals("2")) {
                        counter3++;
                    }
                }
            } catch (SQLException sql) {
                throw new IllegalArgumentException("No data", sql);
            }
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot select Citizen based on ID", sql);
        }

        result = List.of(counter1, counter2, counter3);
        return result;
    }

    public List<Citizen> dailyVaccinationBasedOnZip(String zip) {
        List<Citizen> result = new ArrayList<>();


        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("SELECT citizen_name, zip, age, email, taj, vaccination_type, number_of_vaccination FROM citizens Left JOIN vaccinations " +
                                "ON citizens.citizen_id = vaccinations.citizen_id WHERE zip = ? and NUMBER_of_vaccination <=1 " +
                                "AND (last_vaccination  < ? OR last_vaccination is NULL) " +
                                "ORDER BY `age` DESC, citizen_name " +
                                "LIMIT 16"
                        )
        ) {
            ps.setString(1, zip);
            ps.setString(2, LocalDate.now().minusDays(15).toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(
                            new Citizen(
                                    rs.getString("citizen_name"),
                                    rs.getString("zip"),
                                    rs.getInt("age"),
                                    rs.getString("email"),
                                    rs.getString("taj"),
                                    rs.getString("vaccination_type"),
                                    rs.getInt("number_of_vaccination")));
                }
            } catch (SQLException sql) {
                throw new IllegalArgumentException("No data", sql);
            }
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot select Citizen based on ID", sql);
        }


        return result;
    }

    public String noteOfVaccinationFailed(String taj) {
        String typeOfVaccina = null;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("select note from vaccinations where citizen_id = ?");
        ) {
            ps.setInt(1, searchCitizenIdBasedOnTaj(taj));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    typeOfVaccina = rs.getString("note");

                }
            } catch (SQLException sql) {
                throw new IllegalArgumentException("No data", sql);
            }
        } catch (SQLException sql) {
            throw new IllegalStateException("Cannot select Citizen based on ID", sql);
        }
        return typeOfVaccina;
    }
}






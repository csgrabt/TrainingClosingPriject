package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static covid.businesslogiclayer.language.MessageHun.*;


public class MethodsToProgramWorking {
    private static final CitizenDao cd = new CitizenDao();

    public static StringBuilder vaccinationListToPrint(List<Citizen> citizens) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatedFileFirstRow());
        String template;

        try (BufferedReader bf = Files.newBufferedReader(Path.of("template_closingproject.txt"))) {

            template = bf.readLine();

            for (int i = 0; i < citizens.size(); i++) {
                String template1 = template.replace("{date}", (LocalTime.of(8, 00).plusMinutes(30 * i)).toString())
                        .replace("{name}", citizens.get(i).getFullName())
                        .replace("{ZIP}", citizens.get(i).getZipCode())
                        .replace("{age}", Integer.toString(citizens.get(i).getAge()))
                        .replace("{email}", citizens.get(i).getEmail())
                        .replace("{taj}", citizens.get(i).getHealthInsuranceNumber())
                        .replace("{number_of_vaccination}", Integer.toString(citizens.get(i).getNumberOfVaccination()));

                sb.append(template1);

                sb.append("\n");
            }


        } catch (IOException ioe) {
            throw new IllegalArgumentException(errorMessageBufferedWriter(), ioe);
        }


        return sb;
    }


    public static void writeTheNamesBasedOnZipToPrint(StringBuilder sb) {
        Path path = Path.of(LocalDate.now().toString() + generatedFileNamesEnd());

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(sb.toString());
        } catch (IOException ioe) {
            System.out.println(errorDuringWriteTheFile() + ioe.getMessage());
        }
    }

    public static void dailyVaccinationsBasedOnZip(String zip) {
        writeTheNamesBasedOnZipToPrint(
                vaccinationListToPrint(cd.dailyVaccinationBasedOnZip(zip)));
    }
}




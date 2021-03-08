package covid.fileprocess;

import covid.businesslogiclayer.Citizen;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileReaderToCovidProject {

    public Citizen fileReader(String str, String regex) throws IOException {
        Path path1 = Path.of(str);
        Citizen cz = null;
        int counter = 0;
        String line;
        try (BufferedReader bf = Files.newBufferedReader(path1)) {

            bf.readLine();
            while ((line = bf.readLine()) != null) {
                counter++;
                String[] citizenArray = line.split(regex);
                cz = new Citizen(
                        citizenArray[0],
                        citizenArray[1],
                        Integer.parseInt(citizenArray[2]),
                        citizenArray[3],
                        citizenArray[4]
                );

            }

        } catch (ArrayIndexOutOfBoundsException ioe) {
            throw new IllegalArgumentException("Something went wrong", ioe);
        }

        return cz;
    }

}

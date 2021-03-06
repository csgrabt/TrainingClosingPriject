package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static covid.businesslogiclayer.language.MessageHun.*;

public class SecondMenuMethods {

    private static final CitizenDao cd = new CitizenDao();


    public static void dataProcessing(Scanner scanner, String path) {
        try {
            if (!(Files.exists(Path.of(path)))) {
                throw new IllegalArgumentException(secondExeptionMassege1());
            }
            secondMenuPritnln2();
            String regex = scanner.nextLine();
            cd.writeRegisterFromFileToDb(path, regex);
        } catch (IllegalArgumentException ioe) {
            secondMenuPritnln3(ioe);
        }
    }



}

package covid.presentationlayer;

import java.util.Scanner;

import static covid.businesslogiclayer.MethodsToProgramWorking.dailyVaccinationsBasedOnZip;
import static covid.businesslogiclayer.language.MessageHun.giveTheZipCode;

public class ThirdMenuPoint {

    public static void thirdMenuPoint(Scanner scanner) {
        try {
            giveTheZipCode();
            String zip = scanner.nextLine();
            dailyVaccinationsBasedOnZip(zip);
        } catch (IllegalArgumentException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


}




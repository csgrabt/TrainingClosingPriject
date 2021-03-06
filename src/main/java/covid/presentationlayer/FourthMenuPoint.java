package covid.presentationlayer;

import java.util.List;
import java.util.Scanner;

import static covid.businesslogiclayer.FourthMenuMethods.*;
import static covid.businesslogiclayer.language.MessageHun.*;


public class FourthMenuPoint {

    public static void fourthMenuPoint(Scanner scanner, List<String> listVaccinaMenu) {
        try {

            giveTheTajNumber();
            String taj = scanner.nextLine();

            int citizen_id = getCitizenIdBasedOnTaj(taj);

            int numberOfVaccinations = getNumberOfVaccinations(taj);


            switch (numberOfVaccinations) {
                case 0:
                    recordingVaccination(scanner, citizen_id, numberOfVaccinations, listVaccinaMenu);
                    break;
                case 1:
                    printTheNumberOfVaccination(numberOfVaccinations);
                    dateOfVaccinationFromDB(taj);
                    typeOfVaccinationFromDB(taj);
                    recordingVaccination(scanner, citizen_id, numberOfVaccinations, listVaccinaMenu);

                    break;
                default:
                    defultMessageSwithIn4thMenu(numberOfVaccinations);
            }

            dataRecorded();

        } catch (IllegalArgumentException ioe) {
            System.out.println(ioe.getMessage() + backToMainMenu());
        }


    }


}

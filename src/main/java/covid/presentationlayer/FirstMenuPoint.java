package covid.presentationlayer;

import covid.businesslogiclayer.Citizen;

import java.util.Scanner;

import static covid.businesslogiclayer.FirstMenuMethods.citizenToDB;
import static covid.businesslogiclayer.FirstMenuMethods.getCityNameFromDB;
import static covid.businesslogiclayer.language.MessageHun.*;

public class FirstMenuPoint {

    private static final int AGE_DEFAULT = 0;
    private static final String CITY_DEFAULT = "";
    private static final String EMAIL1_DEFAULT = "AAA";
    private static final String EMAIL2_DEFAULT = "BBB";

    public static void firstMenuPoint(Citizen cz, Scanner scanner) {

        String nameOfTheCity = CITY_DEFAULT;
        String zipCode;
        do {
            giveTheZipCode();
            zipCode = scanner.nextLine();

            nameOfTheCity = getCityNameFromDB(cz, nameOfTheCity, zipCode);

        } while (nameOfTheCity.isEmpty());

        System.out.println(nameOfTheCity);

        citizenToDB(setName(cz, scanner), setEmail(cz, scanner), setTajNumber(cz, scanner), setAge(cz, scanner), zipCode);
    }



    private static String setTajNumber(Citizen cz, Scanner scanner) {
        boolean tajValidator = true;
        String taj;
        do {
            giveTheTajNumber();
            taj = scanner.nextLine();
            try {
                cz.validatorHealthInsuranceNumber(taj);
                tajValidator = false;
            } catch (IllegalArgumentException iae) {
                fistMenuPrintln12();
            }
        } while (tajValidator);
        return taj;
    }

    private static String setEmail(Citizen cz, Scanner scanner) {
        String email = EMAIL1_DEFAULT;
        String email2 = EMAIL2_DEFAULT;
        int counterEmail = 0;
        do {
            try {
                fistTimeTryToGiveEmail(counterEmail);
                fistMenuPrintln8();
                email = scanner.nextLine();
                cz.emailValidator(email);
                fistMenuPrintln9();
                email2 = scanner.nextLine();
                counterEmail++;
            } catch (IllegalArgumentException ioe) {
                fistMenuPrintln10();
            }
        } while (!email.equals(email2));
        return email;
    }

    private static void fistTimeTryToGiveEmail(int counterEmail) {
        if (counterEmail != 0) {
            fistMenuPrintln7();
        }
    }

    private static int setAge(Citizen cz, Scanner scanner) {
        int age = AGE_DEFAULT;
        do {
            try {
                fistMenuPrintln5();
                String ageAsAString = scanner.nextLine();
                age = Integer.parseInt(ageAsAString);
            } catch
            (NumberFormatException ioe) {
                fistMenuPrintln6();
            }
        } while (cz.validatorAge(age));
        return age;
    }



    private static String setName(Citizen cz, Scanner scanner) {
        String name;
        int counterName = 0;
        do {
            if (counterName == 0) {
                firstMenuPrintln1();
                name = scanner.nextLine();
                counterName++;
            } else {
                firstMenuPrintln2();
                name = scanner.nextLine();
            }
        } while (cz.validatorName(name));
        return name;
    }
}
package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static covid.businesslogiclayer.language.MessageHun.*;


public class FourthMenuMethods {
    public static final CitizenDao cd = new CitizenDao();

    public static void recordingVaccination(Scanner scanner, int citizen_id, int numberOfVaccinations, List<String> listVaccinaMenu) {
        giveMeTheDate();
        String date = scanner.nextLine();
        try {
            LocalDate dateToDB = LocalDate.parse(date);
            String type;
            type = setTheTypeOfVaccina(scanner, listVaccinaMenu);
            String status = getStatusWhenTheVaccinationIsOk();

           Citizen cz = new Citizen(citizen_id, VaccinesType.valueOf(type.toUpperCase()).getName(), numberOfVaccinations, status, dateToDB);

            cd.vaccinationSetTimeAndType(cz);



        } catch (RuntimeException e) {
            throw new IllegalArgumentException(invalidDateForm() + e.getMessage());
        }
    }

    private static String setTheTypeOfVaccina(Scanner scanner, List<String> listVaccinaMenu) {
        boolean vaccinaMenu = true;
        String type;
        do {
            typeOfVaccina();
            type = scanner.nextLine();
        if (listVaccinaMenu.contains(type.toUpperCase())){
         vaccinaMenu = false;
        }else{
            System.out.println("A megadott menüpont nem létezik/nem elérhető vakcina, adja meg a típust újra!");
        }
        }while(vaccinaMenu);
        return type;
    }

    public static Integer getNumberOfVaccinations(String taj) {
        return cd.numberOfVaccination(taj);
    }

    public static Integer getCitizenIdBasedOnTaj(String taj) {
        return cd.searchCitizenIdBasedOnTaj(taj);
    }

    public static void typeOfVaccinationFromDB(String taj) {
        System.out.println(typeOfTheVaccination() + cd.typeOfVaccination(taj));
    }

    public static void dateOfVaccinationFromDB(String taj) {
        System.out.println(dateOfTheLastVaccination() + cd.dateOfVaccination(taj));
    }
}

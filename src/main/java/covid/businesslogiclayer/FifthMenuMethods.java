package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import java.time.LocalDate;

public class FifthMenuMethods {
    private static final CitizenDao cd = new CitizenDao();

    public static void failedVaccinationRegister(String taj, String note, String date, String status) {
        try {
            int citizen_id = cd.searchCitizenIdBasedOnTaj(taj);
            LocalDate timeToDB = LocalDate.parse(date);
            cd.failedVaccination(timeToDB, note, citizen_id, status);

        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}

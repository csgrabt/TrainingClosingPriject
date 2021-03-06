package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import static covid.businesslogiclayer.language.MessageHun.fistMenuPrintln13;
import static covid.businesslogiclayer.language.MessageHun.fistMenuPrintln4;

public class FirstMenuMethods{

    private static final CitizenDao cd = new CitizenDao();

    public static String getCityNameFromDB(Citizen cz, String nameOfTheCity, String zipCode) {
        if (cz.validatorZipCode(zipCode))
            try {
                {
                    nameOfTheCity = cd.findCityByZipCode(zipCode);
                }
            } catch (IllegalArgumentException ie) {
                fistMenuPrintln4(zipCode);
            }
        return nameOfTheCity;
    }

    public static void citizenToDB(String name, String email, String taj, int age, String zipCode) {
        try {
            Citizen citizen = new Citizen(name, zipCode, age, email, taj);
            cd.writeRegistrationToDB(citizen);
        } catch (
                IllegalArgumentException ioe
        ) {
            fistMenuPrintln13();
        }
    }



}

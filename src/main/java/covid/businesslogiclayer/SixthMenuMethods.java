package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import java.util.List;

public class SixthMenuMethods {
    public static final CitizenDao cd = new CitizenDao();

    public static String getCityByZipcode(String zipCode) {
        return cd.findCityByZipCode(zipCode);
    }

    public static List<Integer> getListToStatistic(String zipCode) {
        return cd.statisticBasedOnZip(zipCode);

    }


}

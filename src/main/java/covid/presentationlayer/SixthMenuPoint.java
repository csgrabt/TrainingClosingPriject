package covid.presentationlayer;

import java.util.List;
import java.util.Scanner;

import static covid.businesslogiclayer.SixthMenuMethods.getCityByZipcode;
import static covid.businesslogiclayer.SixthMenuMethods.getListToStatistic;
import static covid.businesslogiclayer.language.MessageHun.*;

public class SixthMenuPoint {
    public static void sixthMenuPoint(Scanner scanner) {

        System.out.println(giveTheZipCodeForTheStatic());
        String zipCode = scanner.nextLine();
        try {
            List<Integer> statistic = getListToStatistic(zipCode);
            System.out.println(
                    getCityByZipcode(zipCode) + resultNote1() + (statistic.get(0) + statistic.get(1) + statistic.get(2)) + resultNote2() +
                            resultNote4() + statistic.get(0) + newLine() +
                            resultNote5() + statistic.get(1) + newLine() +
                            resultNote6() + statistic.get(2)
            );
        } catch (IllegalArgumentException ioe) {
            System.out.println(ioe);
        }

    }


}


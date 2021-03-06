package covid.presentationlayer;

import java.util.Scanner;

import static covid.businesslogiclayer.SecondMenuMethods.dataProcessing;
import static covid.businesslogiclayer.language.MessageHun.secondMenuPritnln1;
import static covid.businesslogiclayer.language.MessageHun.secondMenuPritnln4;


public class SecondMenuPoint {

    public static void secondMenuPoint(Scanner scanner) {

        try {
            secondMenuPritnln1();
            String path = scanner.nextLine();
            dataProcessing(scanner, path);
        } catch (ArrayIndexOutOfBoundsException ie) {
            secondMenuPritnln4(ie);
        }
    }


}

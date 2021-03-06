package covid.presentationlayer;

import covid.businesslogiclayer.ProjectConfig;

import java.util.Scanner;

import static covid.businesslogiclayer.language.MessageHun.*;

public class TheMenu {
    public static void menu(ProjectConfig sys, Scanner scanner) {
        String progress;
        do {
            printTheMenuPoints(sys);
            giveMeANumber(sys);
            int number = menuSelection(sys);
            switch (number) {
                case 1 -> FirstMenuPoint.firstMenuPoint(sys.getCz(), scanner);
                case 2 -> SecondMenuPoint.secondMenuPoint(scanner);
                case 3 -> ThirdMenuPoint.thirdMenuPoint(scanner);
                case 4 -> FourthMenuPoint.fourthMenuPoint(scanner, sys.getVaccinaMenu());
                case 5 -> FifthMenuPoint.fifthMenuPoint(scanner);
                case 6 -> SixthMenuPoint.sixthMenuPoint(scanner);
            }
            if (exit(number)) return;
            exitOrMainMenuAgain();
            progress = scanner.nextLine();
        } while (progress.equals(sys.MENU_CONTROL));
    }


    private static boolean exit(int number) {
        if(number == 7){
            return true;
        }
        return false;
    }


    private static int menuSelection(ProjectConfig sys) {
        int number = 0;
        boolean menu = true;
        do {
            String numberOfMenu = sys.getScanner().nextLine();

            if (sys.getMenuPoints().contains(numberOfMenu)) {
                number = Integer.parseInt(numberOfMenu);
                menu = false;

            } else {
                menuPointIsNotExits();
            }
        } while (menu);
        return number;
    }


}

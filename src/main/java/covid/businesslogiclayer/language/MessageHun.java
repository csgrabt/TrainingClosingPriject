package covid.businesslogiclayer.language;

import covid.businesslogiclayer.ProjectConfig;
import covid.businesslogiclayer.VaccinesType;

public class MessageHun {

    public static void exitOrMainMenuAgain() {
        System.out.println("Mit kíván tenni? \n Üssön 1-est majd entert, hogy visszatérjen a főmenübe! \n Minden más karakter bevitelével kilép a programból!");
    }

    public static void menuPointIsNotExits() {
        System.out.println("Nem létező menüpont!");
    }

    public static void giveMeANumber(ProjectConfig pc) {
        System.out.println(pc.getLanguage().getMessage("7"));
    }

    public static void printTheMenuPoints(ProjectConfig pc) {
        System.out.println(
                pc.getLanguage().getMessage("1") + "\n" +
                        pc.getLanguage().getMessage("2") + "\n" +
                        pc.getLanguage().getMessage("3") + "\n" +
                        pc.getLanguage().getMessage("4") + "\n" +
                        pc.getLanguage().getMessage("5") + "\n" +
                        pc.getLanguage().getMessage("6") +"\n" +
                        pc.getLanguage().getMessage("8"));
    }

    public static void fistMenuPrintln13() {
        System.out.println("Hiba történt az adatbázis írása sorám (Esetleg az adatbázis már tartalmazza a taj számot)!");
    }

    public static void fistMenuPrintln12() {
        System.out.println("Hibás taj szám!");
    }

    public static void giveTheTajNumber() {
        System.out.println("Adja meg a TAJ számot!");
    }

    public static void fistMenuPrintln10() {
        System.out.println("Az email formátuma nem megfelelő");
    }

    public static void fistMenuPrintln9() {
        System.out.println("Ismételje  meg az email címet!");
    }

    public static void fistMenuPrintln8() {
        System.out.println("Adja meg az email címet!");
    }

    public static void fistMenuPrintln7() {
        System.out.println("A két eamik cím nem egyezik!");
    }

    public static void fistMenuPrintln6() {
        System.out.println("Az életkort számmal kell megadni, ne legyen szóköz a végén!");
    }

    public static void fistMenuPrintln5() {
        System.out.println("Adja meg az életkort!");
    }

    public static void fistMenuPrintln4(String zipCode) {
        System.out.println("A táblázat nem tartalmazza a megadott irányító számot " + zipCode);
    }

    public static void giveTheZipCode() {
        System.out.println("Adja meg az irányítószámot!");
    }

    public static void firstMenuPrintln2() {
        System.out.println("Helytelen név páciens nevét!");
    }

    public static void firstMenuPrintln1() {
        System.out.println("Adja meg a páciens nevét!");
    }

    public static String secondExeptionMassege1() {
        return "A fájl nem létezik!";
    }

    public static void secondMenuPritnln4(ArrayIndexOutOfBoundsException ie) {
        System.out.println(ie.toString() + " Hiba a feldolgozás közben");
    }

    public static void secondMenuPritnln3(IllegalArgumentException ioe) {
        System.out.println(" A művelet egy hiba miatt megszakadt!" + ioe);
    }

    public static void secondMenuPritnln2() {
        System.out.println("Adja meg az elválasztó karaktert a fájlon belül!");
    }

    public static void secondMenuPritnln1() {
        System.out.println("Adja meg a fájl elérési útvonalát(pl: C:/alma/alma.txt)!");
    }

    public static void defultMessageSwithIn4thMenu(int numberOfVaccinations) {
        System.out.println(("Oltások száma: " + numberOfVaccinations + ", nincs szükség több oltásra! "));
    }

    public static void dataRecorded() {
        System.out.println("Minden adatot rögzítettünk!");
    }

    public static String backToMainMenu() {
        return "Térjen vissza a főmenübe és kezdje elölről!";
    }


    public static String invalidDateForm() {
        return "Invalid data form";
    }


    public static void typeOfVaccina() {
        System.out.println("Valassza ki a vakcian típusát:\n" +
                "A. " + VaccinesType.A.getName() + "\n" +
                "B. " + VaccinesType.B.getName() + "\n" +
                "C. " + VaccinesType.C.getName() + "\n" +
                "D. " + VaccinesType.D.getName() + "\n" +
                "E. " + VaccinesType.E.getName() + "\n" +
                "F. " + VaccinesType.F.getName() + "\n" +
                "G. " + VaccinesType.G.getName());

    }

    public static void giveMeTheDate() {
        System.out.println("Adja meg a dátumot (yyyy-mm-dd)!");
    }

    public static String generatedFileFirstRow() {
        return "date;name;ZIP;age;email;taj;number_of_vaccination\n";
    }

    public static String errorMessageBufferedWriter() {
        return "Something went wrong";
    }

    public static String errorDuringWriteTheFile() {
        return "Hiba az írás sorám";
    }

    public static String generatedFileNamesEnd() {
        return "_namesToVaccination";

    }

    public static String getStatusWhenTheVaccinationIsOk() {
        return "OK";
    }

    public static String statusWhenVaccinationIsNotOk() {
        return "not ok";
    }

    public static void whyItFailed() {
        System.out.println("Adja meg a meghiúsúlás okát:");
    }

    public static void printTheNumberOfVaccination(int numberOfVaccinations) {
        System.out.println("Oltások száma: " + numberOfVaccinations);
    }

    public static String typeOfTheVaccination() {
        return "Az oltás típusa: ";
    }

    public static String dateOfTheLastVaccination() {
        return "Az oltás dátuma: ";
    }

    public static String newLine() {
        return "\n";
    }

    public static String resultNote6() {
        return "2. oltást megkapottak száma: ";
    }

    public static String resultNote5() {
        return "1. oltást megkapottak száma: ";
    }

    public static String resultNote4() {
        return "Nem oltottak száma: ";
    }

    public static String resultNote2() {
        return "):" + newLine();
    }

    public static String resultNote1() {
        return " város/település statisztikái (regisztrált lakosok száma: ";
    }

    public static String giveTheZipCodeForTheStatic() {
        return "Adja meg az irányítószámot, ami alapján szürni szeretne!";
    }


}
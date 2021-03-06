package covid.businesslogiclayer;

public enum VaccinesType {

    A("Pfizer-BioNtech"), B("Moderna"), C("AstraZeneca"),
    D("Szputynik"), E("Sinopharm"),
    F("Janssen - Nem elérhető még"), G("CureVac - Nem elérhető még");


    private String name;

    VaccinesType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

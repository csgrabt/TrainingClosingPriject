package covid.businesslogiclayer.language;

public enum Language {
    A("/hun.txt"), B("/eng.txt");

    private String filename;

    public String getFilename() {
        return filename;
    }

    Language(String filename) {
        this.filename = filename;
    }
}

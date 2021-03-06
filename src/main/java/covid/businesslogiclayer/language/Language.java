package covid.businesslogiclayer.language;

public enum Language {
    A("/hun.properties"), B("/eng.properties");

    private String filename;

    public String getFilename() {
        return filename;
    }

    Language(String filename) {
        this.filename = filename;
    }
}

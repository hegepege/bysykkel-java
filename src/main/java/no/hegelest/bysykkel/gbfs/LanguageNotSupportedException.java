package no.hegelest.bysykkel.gbfs;

public class LanguageNotSupportedException extends RuntimeException {

    public final String language;

    public LanguageNotSupportedException(String language) {
        super(String.format("Could not find data for language %s", language));
        this.language = language;
    }
}

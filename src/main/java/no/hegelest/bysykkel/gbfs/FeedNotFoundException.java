package no.hegelest.bysykkel.gbfs;

public class FeedNotFoundException extends RuntimeException {
    public final String feedName;

    public FeedNotFoundException(String feedName) {
        super(String.format("fant ikke feed med navn %s", feedName));

        this.feedName = feedName;
    }
}

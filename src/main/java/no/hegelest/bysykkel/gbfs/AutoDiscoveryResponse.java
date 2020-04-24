package no.hegelest.bysykkel.gbfs;

import java.util.Map;

public class AutoDiscoveryResponse extends GbfsResponse<Map<String, FeedList>> {

    public FeedList data(String language) {
        if (data.get(language) == null) {
            throw new LanguageNotSupportedException(language);
        }
        return data.get(language);
    }
}

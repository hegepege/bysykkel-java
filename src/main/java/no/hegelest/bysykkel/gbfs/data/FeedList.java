package no.hegelest.bysykkel.gbfs.data;

import no.hegelest.bysykkel.gbfs.FeedNotFoundException;
import no.hegelest.bysykkel.gbfs.data.Feed;

import java.util.List;

public class FeedList {
    public List<Feed> feeds;

    public String findStationInformationUrl() {
        return feeds
                .stream()
                .filter(feed -> feed.name.equals("station_information"))
                .map(Feed::url)
                .findFirst()
                .orElseThrow(() -> new FeedNotFoundException("station_information"));
    }

    public String findStationStatusUrl() {
        return feeds
                .stream()
                .filter(feed -> feed.name.equals("station_status"))
                .map(Feed::url)
                .findFirst()
                .orElseThrow(() -> new FeedNotFoundException("station_status"));
    }
}

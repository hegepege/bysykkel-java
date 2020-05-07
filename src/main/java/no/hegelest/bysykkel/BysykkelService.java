package no.hegelest.bysykkel;

import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import no.hegelest.bysykkel.gbfs.AutoDiscoveryResponse;
import no.hegelest.bysykkel.gbfs.StationInformationResponse;
import no.hegelest.bysykkel.gbfs.StationStatusResponse;
import no.hegelest.bysykkel.gbfs.data.StationData;
import org.springframework.web.client.RestTemplate;

public class BysykkelService {

    private final RestTemplate baseQuery;
    private AutoDiscoveryResponse autoDiscovery;
    private Instant nextUpdate;

    public BysykkelService(RestTemplate restTemplate) {
        baseQuery = restTemplate;
    }

    synchronized AutoDiscoveryResponse autoDiscovery() {
        if (autoDiscovery == null || needsRefresh()) {
            autoDiscovery = baseQuery
                    .getForObject("https://gbfs.urbansharing.com/oslobysykkel.no/gbfs.json", AutoDiscoveryResponse.class);
            nextUpdate = Instant.now().plusSeconds(autoDiscovery.ttl);
        }
        return autoDiscovery;
    }

    private boolean needsRefresh() {
        return Instant.now().isAfter(nextUpdate);
    }


    public Stream<Stasjonsinformasjon> getStationInformation(final String prefix) {
        return fetchStationInformation()
                .data
                .stations.stream()
                .filter(stat -> stat.name.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted(Comparator.comparing(StationData::name))
                .map(station -> new Stasjonsinformasjon(station.name, station.station_id));
    }

    public Optional<Stasjonsstatus> getStationStatus(int station_id) {
        return fetchStationStatus()
                .data
                .stations.stream()
                .filter(status -> status.station_id == station_id)
                .findFirst()
                .map(status -> new Stasjonsstatus(status.num_bikes_available, status.num_docks_available));
    }

    private StationStatusResponse fetchStationStatus() {
        try {
            var stationStatusUrl = autoDiscovery().data("nb").findStationStatusUrl();
            return baseQuery.getForObject(stationStatusUrl, StationStatusResponse.class);
        } catch (Exception e) {
            throw new BysykkelServiceException("Could not fetch station status", e);
        }
    }

    private StationInformationResponse fetchStationInformation() {
        try {
            var stationInformationUrl = autoDiscovery().data("nb").findStationInformationUrl();
            return baseQuery.getForObject(stationInformationUrl, StationInformationResponse.class);
        } catch (Exception e) {
            // TODO: hva er det som faktisk kan kaste exception her? er dette for å kun kaste en exception ut av ditt api?
            throw new BysykkelServiceException("Could not fetch station information", e);
        }
    }
}

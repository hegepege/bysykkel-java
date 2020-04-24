package no.hegelest.bysykkel;

import no.hegelest.bysykkel.gbfs.AutoDiscoveryResponse;
import no.hegelest.bysykkel.gbfs.data.StationData;
import no.hegelest.bysykkel.gbfs.StationInformationResponse;
import no.hegelest.bysykkel.gbfs.StationStatusResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Scanner;

@Component
public class BysykkelConsumer implements CommandLineRunner {

    private final RestTemplate baseQuery;

    public BysykkelConsumer(RestTemplate restTemplate) {
        baseQuery = restTemplate;
    }

    private AutoDiscoveryResponse runAutoDiscovery() {
        return baseQuery
                .getForObject("https://gbfs.urbansharing.com/oslobysykkel.no/gbfs.json", AutoDiscoveryResponse.class);
    }

    @Override
    public void run(String... args) {
        Scanner reader = new Scanner(System.in);
        var autoDiscovery = runAutoDiscovery();
        showStationAndStatusInformation(autoDiscovery, "");

        while(true) {
            System.out.print("Skriv de føste bokstavene i stasjonen du er interessert i: ");
            showStationAndStatusInformation(autoDiscovery, reader.nextLine());
            System.out.println();
        }
    }

    private void showStationAndStatusInformation(AutoDiscoveryResponse autoDiscovery, String prefix) {
        StationStatusResponse stationStatuses = fetchStationStatus(autoDiscovery);
        fetchStationInformation(autoDiscovery)
                .data
                .stations.stream()
                .filter(stat -> stat.name.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted(Comparator.comparing(StationData::name))
                .forEach(station ->
                        System.out.println(String.format("\n%s: \n\t Antall ledige sykler: %s\n\t Antall ledige låser:  %s\n",
                                station.name,
                                getAvailableBikes(stationStatuses, station.station_id),
                                getAvailableLocks(stationStatuses, station.station_id)))
                );
    }

    private String getAvailableLocks(StationStatusResponse stationStatuses, int station_id) {
        return stationStatuses
                .data
                .stations.stream()
                .filter(status -> status.station_id == station_id)
                .findFirst()
                .map(status -> Integer.toString(status.num_docks_available))
                .orElse("Ikke tilgjengelig");
    }

    private String getAvailableBikes(StationStatusResponse stationStatuses, int station_id) {
        return stationStatuses
                .data
                .stations.stream()
                .filter(status -> status.station_id == station_id)
                .findFirst()
                .map(status -> Integer.toString(status.num_bikes_available))
                .orElse("Ikke tilgjengelig");
    }

    private StationStatusResponse fetchStationStatus(AutoDiscoveryResponse autoDiscovery) {
        var stationStatusUrl = autoDiscovery.data("nb").findStationStatusUrl();
        return baseQuery.getForObject(stationStatusUrl, StationStatusResponse.class);
    }


    private StationInformationResponse fetchStationInformation(AutoDiscoveryResponse autoDiscovery) {
        var stationInformationUrl = autoDiscovery.data("nb").findStationInformationUrl();
        return baseQuery.getForObject(stationInformationUrl, StationInformationResponse.class);
    }
}

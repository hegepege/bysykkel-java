package no.hegelest.bysykkel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import no.hegelest.bysykkel.gbfs.AutoDiscoveryResponse;
import no.hegelest.bysykkel.gbfs.data.FeedList;
import no.hegelest.bysykkel.gbfs.StationInformationResponse;
import no.hegelest.bysykkel.gbfs.data.StationList;
import no.hegelest.bysykkel.gbfs.StationStatusResponse;
import no.hegelest.bysykkel.gbfs.data.Feed;
import no.hegelest.bysykkel.gbfs.data.StationData;
import no.hegelest.bysykkel.gbfs.data.StationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


@ExtendWith(MockitoExtension.class)
public class BysykkelServiceTest {

    BysykkelService bysykkelService;

    @Mock
    RestTemplate restTemplateMock;

    @BeforeEach
    public void setUp() {
        bysykkelService = new BysykkelService(restTemplateMock);
        doReturn(autoDiscovery()).when(restTemplateMock).getForObject(anyString(), eq(AutoDiscoveryResponse.class));
    }


    @Test
    public void doesNotFailWhenNoStationsAreReturned() {
        setUpStations();
        assertThat(bysykkelService.getStationInformation("")).isEmpty();
    }

    @Test
    public void stationsAreSortedByName() {
        setUpStations("oslo", "bærum", "nesodden");
        assertThat(bysykkelService.getStationInformation("").map(data -> data.navn).collect(Collectors.toList()))
                .containsExactly("bærum", "nesodden", "oslo");

    }

    @Test
    public void stationsHaveStatusInformation() {
        setUpStatus(1);
        assertThat(bysykkelService.getStationStatus(1)).hasValue(new Stasjonsstatus(1, 3));
    }

    @Test
    public void doesNotFailWhenStatusIsMissingForStation() {
        setUpStatus(2);
        assertThat(bysykkelService.getStationStatus(1)).isEmpty();
    }

    @Test
    public void shouldThrowIfFetchStationInformationFails() {
        doThrow(new BysykkelServiceException("test", null)).when(restTemplateMock).getForObject(eq("url"), eq(StationInformationResponse.class));
        assertThatThrownBy(() -> bysykkelService.getStationInformation("")).isInstanceOf(BysykkelServiceException.class);
    }


    private void setUpStatus(int id) {
        StationStatusResponse statusResponse = new StationStatusResponse();
        statusResponse.data = new StationList<>();
        statusResponse.data.stations = new ArrayList<>();
        statusResponse.data.stations =
                List.of(new StationStatus(id, 1, 3));

        doReturn(statusResponse).when(restTemplateMock).getForObject(anyString(), eq(StationStatusResponse.class));
    }

    private void setUpStations(String... names) {
        StationInformationResponse informationResponse = new StationInformationResponse();
        informationResponse.data = new StationList<>();
        informationResponse.data.stations = new ArrayList<>();
        informationResponse.data.stations =
                Arrays.stream(names)
                        .map(name -> new StationData(name, name.hashCode()))
                        .collect(Collectors.toList());

        doReturn(informationResponse).when(restTemplateMock).getForObject(anyString(), eq(StationInformationResponse.class));
    }

    private AutoDiscoveryResponse autoDiscovery() {
        AutoDiscoveryResponse response = new AutoDiscoveryResponse();
        var feeds = new FeedList();
        feeds.feeds = List.of(
                new Feed("station_information", "url"),
                new Feed("station_status", "url")
        );
        response.data = new HashMap<>();
        response.data.put("nb", feeds);
        return response;
    }
}

package no.hegelest.bysykkel;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BysykkelPrinterTest {

    @Mock
    BysykkelService bysykkelService;

    BysykkelPrinter bysykkelPrinter;


    @BeforeEach
    public void setup() {
        bysykkelPrinter = new BysykkelPrinter(bysykkelService);
    }

    @Test
    public void skal_ikke_krasje_dersom_bysykkel_service_kaster_feil() {
        when(bysykkelService.getStationInformation("")).thenThrow(new BysykkelServiceException("feilet!", null));
        assertThatCode(() -> bysykkelPrinter.showStationAndStatusInformation("")).doesNotThrowAnyException();
    }


    @Test
    public void nothing_happens_when_service_does_not_return_station_status() {
        when(bysykkelService.getStationInformation("")).thenReturn(stasjonsInformasjon());
        assertThatCode(() -> bysykkelPrinter.showStationAndStatusInformation("")).doesNotThrowAnyException();
    }

    @Test
    public void nothing_happens_when_service_works_as_expected() {
        when(bysykkelService.getStationStatus(anyInt())).thenReturn(stationStatus());
        when(bysykkelService.getStationInformation("")).thenReturn(stasjonsInformasjon());
        assertThatCode(() -> bysykkelPrinter.showStationAndStatusInformation("")).doesNotThrowAnyException();
    }

    private Optional<Stasjonsstatus> stationStatus() {
        return Optional.of(new Stasjonsstatus(0, 1));
    }

    private Stream<Stasjonsinformasjon> stasjonsInformasjon() {
        return IntStream.range(0, 3)
                .mapToObj(id -> new Stasjonsinformasjon("someName", id));
    }
}
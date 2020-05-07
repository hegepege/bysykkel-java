package no.hegelest.bysykkel.api;

import java.util.stream.Collectors;

import no.hegelest.bysykkel.BysykkelService;
import no.hegelest.bysykkel.Stasjonsinformasjon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BysykkelController {

    private final BysykkelService bysykkelService;

    public BysykkelController(BysykkelService bysykkelService) {
        this.bysykkelService = bysykkelService;
    }

    @GetMapping("/stasjonsinformasjon")
    public StasjonsinformasjonResponse stasjonsInformasjon(@RequestParam(value = "prefix", defaultValue = "") String prefix) {
        var stasjoner = bysykkelService.getStationInformation(prefix)
                .map(station ->
                        new Stasjonsinformasjon(
                                station.navn,
                                station.id
                        )
                ).collect(Collectors.toList());
        stasjoner.forEach(station ->
                bysykkelService.getStationStatus(station.id)
                        .ifPresent(status ->
                                station.status = status
                        )
        );
        return new StasjonsinformasjonResponse(stasjoner);
    }
}

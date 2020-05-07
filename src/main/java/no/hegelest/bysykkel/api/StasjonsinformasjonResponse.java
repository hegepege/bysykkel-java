package no.hegelest.bysykkel.api;

import java.util.List;

import no.hegelest.bysykkel.Stasjonsinformasjon;

public class StasjonsinformasjonResponse {
    public List<Stasjonsinformasjon> stasjonsinformasjonListe;

    public StasjonsinformasjonResponse() {
    }

    public StasjonsinformasjonResponse(final List<Stasjonsinformasjon> stasjonsinformasjonListe) {
        this.stasjonsinformasjonListe = stasjonsinformasjonListe;
    }
}

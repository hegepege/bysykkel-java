package no.hegelest.bysykkel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Stasjonsinformasjon {

    public final String navn;
    @JsonIgnore
    public final int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Stasjonsstatus status;

    public Stasjonsinformasjon(final String navn, final int id) {
        this.navn = navn;
        this.id = id;
    }
}

package no.hegelest.bysykkel;

import java.util.Objects;

public class Stasjonsstatus {

    public int antallLedigeSykler;
    public int antallLedigeLåser;


    public Stasjonsstatus(final int antallLedigeSykler, final int antallLedigeLåser) {
        this.antallLedigeSykler = antallLedigeSykler;
        this.antallLedigeLåser = antallLedigeLåser;
    }

    @Override
    public String toString() {
        return String.format("\tAntall ledige sykler: %s \n\tAntall ledige låser:  %s", antallLedigeSykler, antallLedigeLåser);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stasjonsstatus that = (Stasjonsstatus) o;
        return antallLedigeSykler == that.antallLedigeSykler &&
                antallLedigeLåser == that.antallLedigeLåser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(antallLedigeSykler, antallLedigeLåser);
    }
}

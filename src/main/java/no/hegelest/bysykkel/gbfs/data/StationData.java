package no.hegelest.bysykkel.gbfs.data;

public class StationData {

    public int station_id;
    public String name;
    public String address;
    public String lat;
    public String lon;
    public int capacity;

    public StationData(final String name, final int id) {
        this.name = name;
        this.station_id = id;
    }

    public String name() {
        return name;
    }
}

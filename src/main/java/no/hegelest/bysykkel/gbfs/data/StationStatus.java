package no.hegelest.bysykkel.gbfs.data;


public class StationStatus {

    public boolean is_installed;
    public boolean is_renting;
    public int num_bikes_available;
    public int num_docks_available;
    public boolean is_returning;
    public int station_id;
    public long last_reported;

    public StationStatus(final int id, final int bikesAvailable, final int docksAvailable) {
        this.station_id = id;
        this.num_bikes_available = bikesAvailable;
        this.num_docks_available = docksAvailable;
    }


}

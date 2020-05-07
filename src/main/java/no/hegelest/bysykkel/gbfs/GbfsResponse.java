package no.hegelest.bysykkel.gbfs;

import java.sql.Timestamp;

public class GbfsResponse<T> {
    public int ttl;
    public long last_updated;
    public T data;
}

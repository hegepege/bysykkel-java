package no.hegelest.bysykkel.gbfs;

public class GbfsResponse<T> {
    public int ttl;
    public long last_updated;
    public T data;
}

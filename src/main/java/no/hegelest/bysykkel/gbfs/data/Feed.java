package no.hegelest.bysykkel.gbfs.data;



public class Feed {

    public String name;
    public String url;

    public Feed(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    public String url() {
        return url;
    }

}

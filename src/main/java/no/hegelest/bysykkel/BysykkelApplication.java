package no.hegelest.bysykkel;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BysykkelApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BysykkelApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .defaultHeader("ClientIdentifier", "hegelest-testapplikasjon")
                .build();
    }

    @Bean
    public BysykkelService autoDiscoveryService(RestTemplate restTemplate){
        return new BysykkelService(restTemplate);
    }
}

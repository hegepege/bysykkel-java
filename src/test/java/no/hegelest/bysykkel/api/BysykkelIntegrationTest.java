package no.hegelest.bysykkel.api;


import static org.assertj.core.api.Assertions.assertThat;

import no.hegelest.bysykkel.BysykkelService;
import no.hegelest.bysykkel.BysykkelApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BysykkelApplication.class)
public class BysykkelIntegrationTest {

    @Autowired
    BysykkelService bysykkelService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void verifyThatANonEmptyListIsReturned() {
        var response = restTemplate.getForObject("http://localhost:" + port + "/stasjonsinformasjon", StasjonsinformasjonResponse.class);
        assertThat(response.stasjonsinformasjonListe).isNotEmpty();
    }
}

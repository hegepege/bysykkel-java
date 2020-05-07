package no.hegelest.bysykkel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class BysykkelPrinter implements CommandLineRunner {

    private final BysykkelService bysykkelService;

    public BysykkelPrinter(BysykkelService bysykkelService) {
        this.bysykkelService = bysykkelService;
    }


    @Override
    public void run(String... args) {
        Scanner reader = new Scanner(System.in);
        showStationAndStatusInformation("");

       /* while (true) {
            System.out.print("Skriv de føste bokstavene i stasjonen du er interessert i: ");
            showStationAndStatusInformation(reader.nextLine());
            System.out.println();
        }*/
    }

    void showStationAndStatusInformation(String prefix) {
        try {
            bysykkelService.getStationInformation(prefix)
                    .forEach(station ->
                            System.out.println(String.format("\n%s:\n%s\n",
                                    station.navn,
                                    bysykkelService.getStationStatus(station.id).map(Stasjonsstatus::toString).orElse("\tstatus ikke tilgjengelig")
                                    )
                            )
                    );
        } catch (Exception e) {
            System.out.println("En feil oppstod under uthenting av bysykkel informasjon. Prøv igjen senere");
            //logge feil
        }
    }
}

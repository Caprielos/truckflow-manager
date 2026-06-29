package it.gabriele.truckflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto di avvio dell'applicazione Spring Boot.
 *
 * Per ora il progetto resta basato su domain, application e repository in memoria.
 * Spring Boot aggiunge solo una porta REST per provare gli use case da Postman o browser.
 */
@SpringBootApplication
public class TruckFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(TruckFlowApplication.class, args);
    }
}

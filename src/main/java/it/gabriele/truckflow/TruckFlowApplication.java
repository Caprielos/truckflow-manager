package it.gabriele.truckflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Technical Spring Boot bootstrap for TruckFlow Manager.
 *
 * <p>The application entry point belongs to the infrastructure phase because Spring is currently
 * used only as a wiring and lifecycle mechanism. Domain and application packages remain free from
 * Spring annotations.
 */
@SpringBootApplication(scanBasePackages = "it.gabriele.truckflow.infrastructure.config.spring")
public class TruckFlowApplication {

  public static void main(String[] args) {
    SpringApplication.run(TruckFlowApplication.class, args);
  }
}

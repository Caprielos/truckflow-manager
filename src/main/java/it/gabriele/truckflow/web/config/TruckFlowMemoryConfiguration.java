package it.gabriele.truckflow.web.config;

import it.gabriele.truckflow.application.port.in.parking.AssignParkingSpotUseCase;
import it.gabriele.truckflow.application.port.out.ParkingAssignmentRepository;
import it.gabriele.truckflow.application.port.out.ParkingSpotRepository;
import it.gabriele.truckflow.application.usecase.parking.DefaultAssignParkingSpotUseCase;
import it.gabriele.truckflow.domain.parking.ParkingSpot;
import it.gabriele.truckflow.domain.parking.ParkingSpotType;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.infrastructure.memory.parking.InMemoryParkingAssignmentRepository;
import it.gabriele.truckflow.infrastructure.memory.parking.InMemoryParkingSpotRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurazione temporanea per avviare TruckFlow senza database.
 *
 * <p>Qui Spring crea gli oggetti repository in memoria e lo use case da usare nel controller REST.
 * Più avanti questa configurazione verrà affiancata o sostituita da PostgreSQL/JPA.
 */
@Configuration
public class TruckFlowMemoryConfiguration {

  @Bean
  public ParkingSpotRepository parkingSpotRepository() {
    InMemoryParkingSpotRepository repository = new InMemoryParkingSpotRepository();

    repository.save(
        ParkingSpot.available(
            "DEPOT-MIL-01",
            "100",
            ParkingSpotType.VAN_SPACE,
            7.0,
            3.0,
            false,
            Notes.of("Posto demo per furgoni")));

    repository.save(
        ParkingSpot.available(
            "DEPOT-MIL-01",
            "A12",
            ParkingSpotType.FULL_COMBINATION_SPACE,
            18.75,
            3.2,
            true,
            Notes.of("Posto demo per autoarticolati con presa elettrica")));

    repository.save(
        ParkingSpot.available(
            "DEPOT-MIL-01",
            "B20",
            ParkingSpotType.LONG_COMBINATION_SPACE,
            18.75,
            3.2,
            false,
            Notes.of("Posto demo per autotreni lunghi")));

    return repository;
  }

  @Bean
  public ParkingAssignmentRepository parkingAssignmentRepository() {
    return new InMemoryParkingAssignmentRepository();
  }

  @Bean
  public AssignParkingSpotUseCase assignParkingSpotUseCase(
      ParkingSpotRepository parkingSpotRepository,
      ParkingAssignmentRepository parkingAssignmentRepository) {
    return new DefaultAssignParkingSpotUseCase(parkingSpotRepository, parkingAssignmentRepository);
  }
}

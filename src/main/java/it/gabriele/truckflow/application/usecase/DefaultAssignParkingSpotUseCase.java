package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.AssignParkingSpotUseCase;
import it.gabriele.truckflow.application.port.out.ParkingAssignmentRepository;
import it.gabriele.truckflow.application.port.out.ParkingSpotRepository;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import it.gabriele.truckflow.domain.parking.ParkingSpot;
import java.util.Objects;

/** Caso d'uso: assegnare un posto parcheggio a un mezzo, rimorchio o convoglio pronto. */
public final class DefaultAssignParkingSpotUseCase implements AssignParkingSpotUseCase {

  private final ParkingSpotRepository parkingSpotRepository;
  private final ParkingAssignmentRepository parkingAssignmentRepository;

  public DefaultAssignParkingSpotUseCase(
      ParkingSpotRepository parkingSpotRepository,
      ParkingAssignmentRepository parkingAssignmentRepository) {
    this.parkingSpotRepository =
        Objects.requireNonNull(
            parkingSpotRepository, "Il repository posti parcheggio è obbligatorio.");
    this.parkingAssignmentRepository =
        Objects.requireNonNull(
            parkingAssignmentRepository, "Il repository assegnazioni parcheggio è obbligatorio.");
  }

  @Override
  public ParkingAssignment handle(Command command) {
    Objects.requireNonNull(command, "Il comando assegnazione parcheggio è obbligatorio.");
    ParkingSpot spot =
        parkingSpotRepository.getRequired(command.parkingSpotId(), "Posto parcheggio");
    ParkingAssignment assignment =
        ParkingAssignment.active(
            command.assignmentCode(),
            spot,
            command.parkedResource(),
            command.startedAt(),
            command.notes());
    parkingAssignmentRepository.save(assignment);
    return assignment;
  }
}

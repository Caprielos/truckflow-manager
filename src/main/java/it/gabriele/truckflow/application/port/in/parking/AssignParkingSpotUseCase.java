package it.gabriele.truckflow.application.port.in.parking;

import it.gabriele.truckflow.domain.parking.ParkedResource;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;

public interface AssignParkingSpotUseCase {

  ParkingAssignment handle(Command command);

  record Command(
      String assignmentCode,
      String parkingSpotId,
      ParkedResource parkedResource,
      LocalDateTime startedAt,
      Notes notes) {}
}

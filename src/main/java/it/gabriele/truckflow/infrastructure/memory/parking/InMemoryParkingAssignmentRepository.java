package it.gabriele.truckflow.infrastructure.memory.parking;

import it.gabriele.truckflow.application.port.out.parking.ParkingAssignmentRepository;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per ParkingAssignment. */
public final class InMemoryParkingAssignmentRepository extends InMemoryRepository<ParkingAssignment>
    implements ParkingAssignmentRepository {

  public InMemoryParkingAssignmentRepository() {
    super(item -> item.getAssignmentCode());
  }
}

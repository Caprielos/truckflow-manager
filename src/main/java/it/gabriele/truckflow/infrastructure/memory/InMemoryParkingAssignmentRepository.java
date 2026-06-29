package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ParkingAssignmentRepository;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;

/** Repository in memoria per ParkingAssignment. */
public final class InMemoryParkingAssignmentRepository extends InMemoryRepository<ParkingAssignment> implements ParkingAssignmentRepository {

    public InMemoryParkingAssignmentRepository() {
        super(item -> item.getAssignmentCode());
    }
}

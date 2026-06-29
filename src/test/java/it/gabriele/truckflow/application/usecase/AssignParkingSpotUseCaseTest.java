package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.out.ParkingAssignmentRepository;
import it.gabriele.truckflow.application.port.out.ParkingSpotRepository;
import it.gabriele.truckflow.domain.parking.ParkedResource;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import it.gabriele.truckflow.domain.parking.ParkingSpot;
import it.gabriele.truckflow.domain.parking.ParkingSpotType;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignParkingSpotUseCaseTest {

    @Test
    void shouldAssignReadyArticulatedVehicleToCombinationParkingSpot() {
        InMemoryParkingSpotRepository spotRepository = new InMemoryParkingSpotRepository();
        InMemoryParkingAssignmentRepository assignmentRepository = new InMemoryParkingAssignmentRepository();
        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL",
                "A100",
                ParkingSpotType.FULL_COMBINATION_SPACE,
                18.0,
                3.5,
                true,
                Notes.empty()
        );
        spotRepository.save(spot);

        DefaultAssignParkingSpotUseCase useCase = new DefaultAssignParkingSpotUseCase(spotRepository, assignmentRepository);
        ParkedResource resource = ParkedResource.articulatedVehicle(
                "COMBO-001",
                "TRAC-001",
                "SEMI-001",
                "Trattore + semirimorchio frigo",
                16.5,
                true
        );

        ParkingAssignment assignment = useCase.handle(new it.gabriele.truckflow.application.port.in.AssignParkingSpotUseCase.Command(
                "PARK-001",
                "DEPOT-MIL/A100",
                resource,
                LocalDateTime.of(2026, 6, 29, 18, 30),
                Notes.of("Convoglio pronto per partenza mattina")
        ));

        assertEquals("PARK-001", assignment.getAssignmentCode());
        assertTrue(assignment.isReadyForMission());
        assertEquals(1, assignmentRepository.findAll().size());
    }

    private static final class InMemoryParkingSpotRepository implements ParkingSpotRepository {
        private final List<ParkingSpot> spots = new ArrayList<>();

        @Override
        public Optional<ParkingSpot> findById(String id) {
            return spots.stream()
                    .filter(spot -> id.equals(spot.getFacilityCode() + "/" + spot.getSpotNumber()))
                    .findFirst();
        }

        @Override
        public void save(ParkingSpot aggregate) {
            spots.add(aggregate);
        }

        @Override
        public List<ParkingSpot> findAll() {
            return List.copyOf(spots);
        }
    }

    private static final class InMemoryParkingAssignmentRepository implements ParkingAssignmentRepository {
        private final List<ParkingAssignment> assignments = new ArrayList<>();

        @Override
        public Optional<ParkingAssignment> findById(String id) {
            return assignments.stream()
                    .filter(assignment -> assignment.getAssignmentCode().equals(id))
                    .findFirst();
        }

        @Override
        public void save(ParkingAssignment aggregate) {
            assignments.add(aggregate);
        }

        @Override
        public List<ParkingAssignment> findAll() {
            return List.copyOf(assignments);
        }
    }
}

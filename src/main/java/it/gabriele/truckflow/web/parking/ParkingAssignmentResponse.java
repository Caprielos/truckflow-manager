package it.gabriele.truckflow.web.parking;

import it.gabriele.truckflow.domain.parking.ParkedResource;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import java.time.LocalDateTime;
import java.util.List;

/** DTO di response per mostrare fuori dall'applicazione una ParkingAssignment. */
public record ParkingAssignmentResponse(
    String assignmentCode,
    String facilityCode,
    String spotNumber,
    String resourceType,
    String resourceId,
    String displayName,
    List<String> componentResourceIds,
    double totalLengthMeters,
    boolean active,
    boolean readyForMission,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    String notes) {

  public static ParkingAssignmentResponse fromDomain(ParkingAssignment assignment) {
    ParkedResource resource = assignment.getParkedResource();
    return new ParkingAssignmentResponse(
        assignment.getAssignmentCode(),
        assignment.getFacilityCode(),
        assignment.getSpotNumber(),
        resource.getType().name(),
        resource.getResourceId(),
        resource.getDisplayName(),
        resource.getComponentResourceIds(),
        resource.getTotalLengthMeters(),
        assignment.isActive(),
        assignment.isReadyForMission(),
        assignment.getStartedAt(),
        assignment.getEndedAt().orElse(null),
        assignment.getNotes().getText());
  }
}

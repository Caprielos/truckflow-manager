package it.gabriele.truckflow.web.parking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gabriele.truckflow.application.port.in.parking.AssignParkingSpotUseCase;
import it.gabriele.truckflow.application.port.out.ParkingAssignmentRepository;
import it.gabriele.truckflow.application.port.out.ParkingSpotRepository;
import it.gabriele.truckflow.domain.parking.ParkedResource;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import it.gabriele.truckflow.domain.shared.Notes;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST per il parcheggio.
 *
 * <p>Il controller è la porta HTTP: riceve JSON da Postman/browser e chiama gli use case
 * applicativi. Non contiene regole aziendali profonde: quelle restano in domain e application.
 */
@Tag(name = "Parking")
@RestController
@RequestMapping("/api/parking")
public class ParkingController {

  private final AssignParkingSpotUseCase assignParkingSpotUseCase;
  private final ParkingSpotRepository parkingSpotRepository;
  private final ParkingAssignmentRepository parkingAssignmentRepository;

  public ParkingController(
      AssignParkingSpotUseCase assignParkingSpotUseCase,
      ParkingSpotRepository parkingSpotRepository,
      ParkingAssignmentRepository parkingAssignmentRepository) {
    this.assignParkingSpotUseCase = assignParkingSpotUseCase;
    this.parkingSpotRepository = parkingSpotRepository;
    this.parkingAssignmentRepository = parkingAssignmentRepository;
  }

  @Operation(
      summary = "Lista posti parcheggio",
      description = "Restituisce i posti parcheggio demo caricati nel repository in memoria.")
  @GetMapping("/spots")
  public List<ParkingSpotResponse> findParkingSpots() {
    return parkingSpotRepository.findAll().stream().map(ParkingSpotResponse::fromDomain).toList();
  }

  @Operation(
      summary = "Lista assegnazioni parcheggio",
      description = "Restituisce le assegnazioni parcheggio attualmente presenti in memoria.")
  @GetMapping("/assignments")
  public List<ParkingAssignmentResponse> findParkingAssignments() {
    return parkingAssignmentRepository.findAll().stream()
        .map(ParkingAssignmentResponse::fromDomain)
        .toList();
  }

  @Operation(
      summary = "Assegna un posto parcheggio",
      description =
          "Assegna una risorsa, per esempio furgone o camion, a un posto parcheggio disponibile.")
  @PostMapping("/assignments")
  @ResponseStatus(HttpStatus.CREATED)
  public ParkingAssignmentResponse assignParkingSpot(
      @Valid @RequestBody AssignParkingSpotRequest request) {
    ParkedResource parkedResource = toParkedResource(request);

    ParkingAssignment assignment =
        assignParkingSpotUseCase.handle(
            new AssignParkingSpotUseCase.Command(
                request.assignmentCode(),
                request.parkingSpotId(),
                parkedResource,
                request.startedAt(),
                toNotes(request.notes())));

    return ParkingAssignmentResponse.fromDomain(assignment);
  }

  private ParkedResource toParkedResource(AssignParkingSpotRequest request) {
    return switch (request.resourceType()) {
      case VAN ->
          ParkedResource.van(
              request.resourceId(), request.displayName(), request.totalLengthMeters());
      case RIGID_TRUCK ->
          ParkedResource.rigidTruck(
              request.resourceId(), request.displayName(), request.totalLengthMeters());
      case TRACTOR_UNIT ->
          ParkedResource.tractorUnit(
              request.resourceId(), request.displayName(), request.totalLengthMeters());
      case TRAILER ->
          ParkedResource.trailer(
              request.resourceId(), request.displayName(), request.totalLengthMeters());
      case SEMI_TRAILER ->
          ParkedResource.semiTrailer(
              request.resourceId(), request.displayName(), request.totalLengthMeters());
      case ARTICULATED_VEHICLE ->
          ParkedResource.articulatedVehicle(
              request.resourceId(),
              requiredComponent(request, 0, "Il trattore dell'autoarticolato è obbligatorio."),
              requiredComponent(request, 1, "Il semirimorchio dell'autoarticolato è obbligatorio."),
              request.displayName(),
              request.totalLengthMeters(),
              readyForMission(request));
      case TRUCK_AND_TRAILER ->
          ParkedResource.truckAndTrailer(
              request.resourceId(),
              requiredComponent(request, 0, "La motrice dell'autotreno è obbligatoria."),
              requiredComponent(request, 1, "Il rimorchio dell'autotreno è obbligatorio."),
              request.displayName(),
              request.totalLengthMeters(),
              readyForMission(request));
      case EQUIPMENT ->
          ParkedResource.equipment(
              request.resourceId(), request.displayName(), request.totalLengthMeters());
      case OTHER ->
          throw new IllegalArgumentException(
              "Il tipo risorsa OTHER non è ancora supportato dalla prima API parcheggio.");
    };
  }

  private static String requiredComponent(
      AssignParkingSpotRequest request, int index, String message) {
    List<String> componentIds = request.componentResourceIds();
    if (componentIds == null
        || componentIds.size() <= index
        || componentIds.get(index) == null
        || componentIds.get(index).isBlank()) {
      throw new IllegalArgumentException(message);
    }
    return componentIds.get(index);
  }

  private static boolean readyForMission(AssignParkingSpotRequest request) {
    return Boolean.TRUE.equals(request.readyForMission());
  }

  private static Notes toNotes(String notes) {
    if (notes == null || notes.isBlank()) {
      return Notes.empty();
    }
    return Notes.of(notes);
  }
}

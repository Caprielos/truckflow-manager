package it.gabriele.truckflow.web.parking;

import it.gabriele.truckflow.domain.parking.ParkingResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO di request per assegnare una risorsa a un posto parcheggio.
 *
 * <p>DTO significa Data Transfer Object: è l'oggetto che rappresenta i dati JSON ricevuti dall'API.
 * Non è una classe del domain, perché il domain non deve dipendere da REST, JSON o Spring.
 */
public record AssignParkingSpotRequest(
    @NotBlank String assignmentCode,
    @NotBlank String parkingSpotId,
    @NotNull ParkingResourceType resourceType,
    @NotBlank String resourceId,
    @NotBlank String displayName,
    List<String> componentResourceIds,
    @NotNull @Positive Double totalLengthMeters,
    Boolean readyForMission,
    @NotNull LocalDateTime startedAt,
    String notes) {}
